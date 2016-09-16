package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.result.merge.MergeContext;
import com.elong.pb.newdda.client.router.result.router.SqlStatementType;

import java.util.HashSet;
import java.util.Set;

/**
 * sql语句路由结果
 * Created by zhangyong on 2016/7/30.
 */
public final class SqlRouterResult {

    private final Set<SqlExecutionUnit> executionUnits = new HashSet<SqlExecutionUnit>();

    private final SqlStatementType sqlStatementType;

    private final MergeContext mergeContext;

    public SqlRouterResult(SqlStatementType sqlStatementType, MergeContext mergeContext) {
        this.sqlStatementType = sqlStatementType;
        this.mergeContext = mergeContext;
    }

    public SqlStatementType getSqlStatementType() {
        return sqlStatementType;
    }

    public MergeContext getMergeContext() {
        return mergeContext;
    }

}
