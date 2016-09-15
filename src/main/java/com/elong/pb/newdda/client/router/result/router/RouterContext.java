package com.elong.pb.newdda.client.router.result.router;

import java.util.Collection;
import java.util.LinkedHashSet;

public class RouterContext {

    private final Collection<RouterTable> routerTables = new LinkedHashSet<RouterTable>();

    private SqlStatementType sqlStatementType;

    private SqlAppender sqlAppender;

    //====================================================================================================================get set method  start==========================================================

    public Collection<RouterTable> getRouterTables() {
        return routerTables;
    }

    public SqlStatementType getSqlStatementType() {
        return sqlStatementType;
    }

    public void setSqlStatementType(SqlStatementType sqlStatementType) {
        this.sqlStatementType = sqlStatementType;
    }

    public void setSqlAppender(SqlAppender sqlAppender) {
        this.sqlAppender = sqlAppender;
    }

    //====================================================================================================================get set method  end==========================================================

}
