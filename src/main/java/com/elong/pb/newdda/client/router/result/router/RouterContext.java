package com.elong.pb.newdda.client.router.result.router;

import java.util.Collection;
import java.util.LinkedHashSet;

public class RouterContext {

    private final Collection<RouterTable> tables = new LinkedHashSet<RouterTable>();

    private SqlStatementType sqlStatementType;

    public Collection<RouterTable> getTables() {
        return tables;
    }

    public SqlStatementType getSqlStatementType() {
        return sqlStatementType;
    }

    public void setSqlStatementType(SqlStatementType sqlStatementType) {
        this.sqlStatementType = sqlStatementType;
    }

}
