package com.elong.pb.newdda.client.executor.wrapper;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;

import java.sql.PreparedStatement;

/**
 * Created by zhangyong on 2016/10/10.
 */
public class PreparedStatementExecutorWrapper {

    private final PreparedStatement preparedStatement;

    private final SqlExecutionUnit sqlExecutionUnit;

    public PreparedStatementExecutorWrapper(final PreparedStatement preparedStatement, final SqlExecutionUnit sqlExecutionUnit) {
        this.preparedStatement = preparedStatement;
        this.sqlExecutionUnit = sqlExecutionUnit;
    }

    //==================================================get method start ==================================================

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public SqlExecutionUnit getSqlExecutionUnit() {
        return sqlExecutionUnit;
    }

    //==================================================get method end ==================================================

}
