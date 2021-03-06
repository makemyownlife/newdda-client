package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.constants.ThreadPoolConstants;
import com.elong.pb.newdda.client.executor.wrapper.StatementExecutorWrapper;
import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StatementExecutor {

    private static final Logger logger = LoggerFactory.getLogger(StatementExecutor.class);

    //防止多线程环境下,数据没有flush到主内存
    private final Object flushLock = new Object();

    //是否抛出异常
    private final static boolean THROW_EXCEPTION = true;

    //定义线程池
    private final List<StatementExecutorWrapper> statementExecutorWrappers = new ArrayList<StatementExecutorWrapper>();

    private final static int MAX_SIZE = ThreadPoolConstants.CORE_SIZE * 2 + 1;

    private final static int KEEP_ALIVE_TIME = 600;

    private final static int EXECUTE_MAX_TIME = 10;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            ThreadPoolConstants.CORE_SIZE,
            MAX_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100000),
            new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "StatementExecutor_" + this.threadIndex.incrementAndGet());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 添加静态语句对象至执行上下文.
     *
     * @param statementExecutorWrapper 静态语句对象的执行上下文
     */
    public void addStatement(final StatementExecutorWrapper statementExecutorWrapper) {
        statementExecutorWrappers.add(statementExecutorWrapper);
    }

    public List<ResultSet> executeQuery() {
        if (statementExecutorWrappers.size() == 1) {
            return Collections.singletonList(executeQueryInternal(statementExecutorWrappers.get(0)));
        }

        final List<ResultSet> result = new ArrayList<ResultSet>();
        final CountDownLatch countDownLatch = new CountDownLatch(statementExecutorWrappers.size());
        //多线程处理
        for (int i = 0; i < statementExecutorWrappers.size(); i++) {
            final StatementExecutorWrapper statementExecutorWrapper = statementExecutorWrappers.get(i);
            threadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        ResultSet resultSet = executeQueryInternal(statementExecutorWrapper);
                        if (resultSet != null) {
                            synchronized (flushLock) {
                                result.add(resultSet);
                            }
                        }
                    } catch (Throwable e) {
                        logger.error("threadPoolExecutor execute error:" + statementExecutorWrapper.getSqlExecutionUnit(), e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await(EXECUTE_MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("executeQuery Interrupted by some reason :" + e.getMessage());
        }
        return result;
    }

    public int executeUpdate() {
        return executeUpdate(new Updater() {
            @Override
            public int executeUpdate(final Statement statement, final String sql) throws SQLException {
                return statement.executeUpdate(sql);
            }
        });
    }

    public int executeUpdate(final int autoGeneratedKeys) {
        return executeUpdate(new Updater() {
            @Override
            public int executeUpdate(final Statement statement, final String sql) throws SQLException {
                return statement.executeUpdate(sql, autoGeneratedKeys);
            }
        });
    }

    public int executeUpdate(final int[] columnIndexes) {
        return executeUpdate(new Updater() {
            @Override
            public int executeUpdate(final Statement statement, final String sql) throws SQLException {
                return statement.executeUpdate(sql, columnIndexes);
            }
        });
    }

    public int executeUpdate(final String[] columnNames) {
        return executeUpdate(new Updater() {
            @Override
            public int executeUpdate(final Statement statement, final String sql) throws SQLException {
                return statement.executeUpdate(sql, columnNames);
            }
        });
    }

    public boolean execute() {
        return execute(new Executor() {
            @Override
            public boolean execute(final Statement statement, final String sql) throws SQLException {
                return statement.execute(sql);
            }
        });
    }

    public boolean execute(final int autoGeneratedKeys) {
        return execute(new Executor() {
            @Override
            public boolean execute(final Statement statement, final String sql) throws SQLException {
                return statement.execute(sql, autoGeneratedKeys);
            }
        });
    }

    public boolean execute(final int[] columnIndexes) {
        return execute(new Executor() {
            @Override
            public boolean execute(final Statement statement, final String sql) throws SQLException {
                return statement.execute(sql, columnIndexes);
            }
        });
    }

    public boolean execute(final String[] columnNames) {
        return execute(new Executor() {
            @Override
            public boolean execute(final Statement statement, final String sql) throws SQLException {
                return statement.execute(sql, columnNames);
            }
        });
    }

    //============================================================================================= help method start ====================================================================================================

    private ResultSet executeQueryInternal(final StatementExecutorWrapper statementExecutorWrapper) {
        Statement statement = statementExecutorWrapper.getStatement();
        SqlExecutionUnit sqlExecutionUnit = statementExecutorWrapper.getSqlExecutionUnit();
        try {
            String sql = sqlExecutionUnit.getSql();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (Exception ex) {
            logger.error(sqlExecutionUnit + " executeQueryInternal error: ", ex);
            return null;
        }
    }

    private boolean execute(final Executor executor) {
        if (1 == statementExecutorWrappers.size()) {
            return executeInternal(executor, statementExecutorWrappers.iterator().next());
        }

        final List<Boolean> result = new ArrayList<Boolean>();
        final CountDownLatch countDownLatch = new CountDownLatch(statementExecutorWrappers.size());
        //多线程处理
        for (int i = 0; i < statementExecutorWrappers.size(); i++) {
            final StatementExecutorWrapper statementExecutorWrapper = statementExecutorWrappers.get(i);
            threadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        boolean eachResult = executeInternal(executor, statementExecutorWrapper);
                        synchronized (flushLock) {
                            result.add(eachResult);
                        }
                    } catch (Throwable e) {
                        logger.error("threadPoolExecutor execute error:" + statementExecutorWrapper.getSqlExecutionUnit(), e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await(EXECUTE_MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("execute Interrupted by some reason :" + e.getMessage());
        }
        return (null == result || result.isEmpty()) ? false : result.get(0);
    }

    private int executeUpdate(final Updater updater) {
        if (1 == statementExecutorWrappers.size()) {
            return executeUpdateInternal(updater, statementExecutorWrappers.iterator().next());
        }
        final AtomicInteger result = new AtomicInteger(0);
        final CountDownLatch countDownLatch = new CountDownLatch(statementExecutorWrappers.size());
        for (int i = 0; i < statementExecutorWrappers.size(); i++) {
            final StatementExecutorWrapper statementExecutorWrapper = statementExecutorWrappers.get(i);
            threadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        int eachResult = executeUpdateInternal(updater, statementExecutorWrapper);
                        result.addAndGet(eachResult);
                    } catch (Throwable e) {
                        logger.error("threadPoolExecutor executeUpdate error:" + statementExecutorWrapper.getSqlExecutionUnit(), e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await(EXECUTE_MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("executeUpdate Interrupted by some reason :" + e.getMessage());
        }
        return result.get();
    }

    private int executeUpdateInternal(final Updater updater, final StatementExecutorWrapper statementExecutorWrapper) {
        int result;
        try {
            result = updater.executeUpdate(statementExecutorWrapper.getStatement(), statementExecutorWrapper.getSqlExecutionUnit().getSql());
        } catch (final SQLException ex) {
            logger.error(statementExecutorWrapper.getSqlExecutionUnit() + " executeUpdateInternal error");
            ExecutorExceptionHandler.handleException(ex , THROW_EXCEPTION);
            return 0;
        }
        return result;
    }

    private boolean executeInternal(final Executor executor, final StatementExecutorWrapper statementExecutorWrapper) {
        boolean result;
        try {
            result = executor.execute(statementExecutorWrapper.getStatement(), statementExecutorWrapper.getSqlExecutionUnit().getSql());
        } catch (final SQLException ex) {
            logger.error(statementExecutorWrapper.getSqlExecutionUnit() + " executeInternal error");
            ExecutorExceptionHandler.handleException(ex , THROW_EXCEPTION);
            return false;
        }
        return result;
    }

    //============================================================================================= help method end ====================================================================================================

    //======================================================================= interface method start ==================================================================
    private interface Updater {
        int executeUpdate(Statement statement, String sql) throws SQLException;
    }

    private interface Executor {
        boolean execute(Statement statement, String sql) throws SQLException;
    }
    //======================================================================= interface method end ==================================================================

}
