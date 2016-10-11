package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.executor.wrapper.PreparedStatementExecutorWrapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class PreparedStatementExecutor {

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
                    return new Thread(r, "StatementExecutor_" + this.threadIndex.incrementAndGet());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    private List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers;

    public PreparedStatementExecutor(List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers) {
        this.preparedStatementExecutorWrappers = preparedStatementExecutorWrappers;
    }

    public List<ResultSet> executeQuery() {
        List<ResultSet> result = null;
        return result;
    }

    public boolean execute() {
        return false;
    }

}
