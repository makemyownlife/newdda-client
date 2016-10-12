package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.exception.ShardingJdbcException;

public final class ExecutorExceptionHandler {
    
    public static void handleException(final Exception ex ,boolean isExceptionThrown) {
        if (isExceptionThrown) {
            throw new ShardingJdbcException(ex);
        }
    }
}
