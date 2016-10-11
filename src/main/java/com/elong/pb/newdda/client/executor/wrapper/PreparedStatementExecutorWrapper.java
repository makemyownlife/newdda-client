package com.elong.pb.newdda.client.executor.wrapper;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by zhangyong on 2016/10/10.
 */
public class PreparedStatementExecutorWrapper {

    private final PreparedStatement preparedStatement;

    private final SqlExecutionUnit sqlExecutionUnit;

    private final List<Object> parameters;

    public PreparedStatementExecutorWrapper(final PreparedStatement preparedStatement, List<Object> parameters, final SqlExecutionUnit sqlExecutionUnit) {
        this.preparedStatement = preparedStatement;
        this.parameters = parameters;
        this.sqlExecutionUnit = sqlExecutionUnit;
    }

    //==================================================get method start ==================================================

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public SqlExecutionUnit getSqlExecutionUnit() {
        return sqlExecutionUnit;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    //==================================================get method end ==================================================

}
