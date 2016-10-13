package com.elong.pb.newdda.client.executor;

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

    private final Object fulshLock = new Object();

    private final static int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private final static int MAX_SIZE = CORE_SIZE * 2 + 1;

    private final static int KEEP_ALIVE_TIME = 600;

    private final static int EXECUTE_MAX_TIME = 10;

    private static ThreadPoolExecutor prepareThreadPoolExecutor = new ThreadPoolExecutor(
            CORE_SIZE,
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
                            synchronized (fulshLock) {
                                result.add(resultSet);
                            }
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
            logger.error("executeQuery Interrupted by some reason :" + e.getMessage());
        }
        return result;
    }

    public boolean execute() {
        return false;
    }

    private ResultSet executePrepareQueryInternal(final PreparedStatementExecutorWrapper preparedStatementExecutorWrapper) {
        ResultSet result;
        try {
            result = preparedStatementExecutorWrapper.getPreparedStatement().executeQuery();
        } catch (final SQLException ex) {
            logger.error(preparedStatementExecutorWrapper.getSqlExecutionUnit() + " executeQueryInternal error: ", ex);
            return null;
        }
        return result;
    }

}
