package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.executor.wrapper.PreparedStatementExecutorWrapper;

import java.sql.ResultSet;
import java.util.List;

public final class PreparedStatementExecutor {

    private  List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers;

    public List<ResultSet> executeQuery() {
        List<ResultSet> result = null;
        return result;
    }

    public boolean execute() {
        return false;
    }

}
