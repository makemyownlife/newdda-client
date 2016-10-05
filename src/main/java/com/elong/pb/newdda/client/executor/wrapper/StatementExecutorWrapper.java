package com.elong.pb.newdda.client.executor.wrapper;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;

import java.sql.Statement;

/**
 * 
 * Created by zhangyong on 16/10/4.
 */
public class StatementExecutorWrapper {

    private final Statement statement;

    private final SqlExecutionUnit sqlExecutionUnit;

    public StatementExecutorWrapper(final Statement statement, final SqlExecutionUnit sqlExecutionUnit) {
        this.statement = statement;
        this.sqlExecutionUnit = sqlExecutionUnit;
    }

    //==================================================get method start ==================================================

    public Statement getStatement() {
        return statement;
    }

    public SqlExecutionUnit getSqlExecutionUnit() {
        return sqlExecutionUnit;
    }

    //==================================================get method end ==================================================

}
