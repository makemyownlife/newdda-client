package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.constants.ThreadPoolConstants;
import com.elong.pb.newdda.client.executor.wrapper.PreparedStatementExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class PreparedStatementExecutor {

    private final static Logger logger = LoggerFactory.getLogger(PreparedStatementExecutor.class);

    private final Object flushLock = new Object();

    private final static int MAX_SIZE = ThreadPoolConstants.CORE_SIZE * 2 + 1;

    private final static int KEEP_ALIVE_TIME = 600;

    private final static int EXECUTE_MAX_TIME = 10;

    private final boolean IS_EXCEPTION_THROWN = true;

    private static ThreadPoolExecutor prepareThreadPoolExecutor = new ThreadPoolExecutor(
            ThreadPoolConstants.CORE_SIZE,
            MAX_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100000),
            new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "PrepareStatementExecutor_" + this.threadIndex.incrementAndGet());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    private List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers;

    public PreparedStatementExecutor(List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers) {
        this.preparedStatementExecutorWrappers = preparedStatementExecutorWrappers;
    }

    public List<ResultSet> executeQuery() {
        if (preparedStatementExecutorWrappers.size() == 1) {
            return Collections.singletonList(executePrepareQueryInternal(preparedStatementExecutorWrappers.get(0)));
        }
        final List<ResultSet> result = new ArrayList<ResultSet>();
        final CountDownLatch countDownLatch = new CountDownLatch(preparedStatementExecutorWrappers.size());
        //多线程处理
        for (int i = 0; i < preparedStatementExecutorWrappers.size(); i++) {
            final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper = preparedStatementExecutorWrappers.get(i);
            prepareThreadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        ResultSet resultSet = executePrepareQueryInternal(preparedStatementExecutorWrapper);
                        if (resultSet != null) {
                            synchronized (flushLock) {
                                result.add(resultSet);
                            }
                        }
                    } catch (Throwable e) {
                        logger.error("prepareThreadPoolExecutor executeQuery error:" + preparedStatementExecutorWrapper.getSqlExecutionUnit(), e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await(EXECUTE_MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("prepareExecuteQuery Interrupted by some reason :" + e.getMessage());
        }
        return result;
    }

    public int executeUpdate() {
        if (preparedStatementExecutorWrappers.size() == 1) {
            return executePrepareUpdateInternal(preparedStatementExecutorWrappers.get(0));
        }

        final AtomicInteger result = new AtomicInteger(0);
        final CountDownLatch countDownLatch = new CountDownLatch(preparedStatementExecutorWrappers.size());
        for (int i = 0; i < preparedStatementExecutorWrappers.size(); i++) {
            final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper = preparedStatementExecutorWrappers.get(i);
            prepareThreadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        int eachResult = executePrepareUpdateInternal(preparedStatementExecutorWrapper);
                        result.addAndGet(eachResult);
                    } catch (Throwable e) {
                        logger.error("prepareThreadPoolExecutor executeUpdate error:" + preparedStatementExecutorWrapper.getSqlExecutionUnit(), e);
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

    public boolean execute() {
        if (1 == preparedStatementExecutorWrappers.size()) {
            PreparedStatementExecutorWrapper preparedStatementExecutorWrapper = preparedStatementExecutorWrappers.iterator().next();
            return executePrepareInternal(preparedStatementExecutorWrapper);
        }

        final List<Boolean> result = new ArrayList<Boolean>();
        final CountDownLatch countDownLatch = new CountDownLatch(preparedStatementExecutorWrappers.size());
        //多线程处理
        for (int i = 0; i < preparedStatementExecutorWrappers.size(); i++) {
            final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper = preparedStatementExecutorWrappers.get(i);
            prepareThreadPoolExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        boolean eachResult = executePrepareInternal(preparedStatementExecutorWrapper);
                        synchronized (flushLock) {
                            result.add(eachResult);
                        }
                    } catch (Throwable e) {
                        logger.error("prepareThreadPoolExecutor execute error:" + preparedStatementExecutorWrapper.getSqlExecutionUnit(), e);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }
        try {
            countDownLatch.await(EXECUTE_MAX_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("prepareExecuteQuery Interrupted by some reason :" + e.getMessage());
        }
        return (null == result || result.isEmpty()) ? false : result.get(0);
    }

    private ResultSet executePrepareQueryInternal(final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper) {
        ResultSet result;
        try {
            result = preparedStatementExecutorWrapper.getPreparedStatement().executeQuery();
        } catch (final SQLException ex) {
            logger.error(preparedStatementExecutorWrapper.getSqlExecutionUnit() + " executePrepareQueryInternal error: ", ex);
            ExecutorExceptionHandler.handleException(ex, IS_EXCEPTION_THROWN);
            return null;
        }
        return result;
    }

    private boolean executePrepareInternal(final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper) {
        boolean result;
        try {
            result = preparedStatementExecutorWrapper.getPreparedStatement().execute();
        } catch (final SQLException ex) {
            logger.error(preparedStatementExecutorWrapper.getSqlExecutionUnit() + " executePrepareInternal error: ", ex);
            ExecutorExceptionHandler.handleException(ex, IS_EXCEPTION_THROWN);
            result = false;
        }
        return result;
    }

    private int executePrepareUpdateInternal(final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper) {
        int result;
        try {
            result = preparedStatementExecutorWrapper.getPreparedStatement().executeUpdate();
        } catch (final SQLException ex) {
            ExecutorExceptionHandler.handleException(ex, IS_EXCEPTION_THROWN);
            return 0;
        }
        return result;
    }

}
