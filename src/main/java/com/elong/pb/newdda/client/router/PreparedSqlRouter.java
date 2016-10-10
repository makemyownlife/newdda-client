package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.parser.SqlParserResult;
import com.elong.pb.newdda.client.router.result.router.ConditionContext;

import java.util.List;

public class PreparedSqlRouter {

    private final String logicSql;

    private final SqlRouterEngine sqlRouterEngine;

    private SqlParserResult sqlParserResult;

    public PreparedSqlRouter(String logicSql, SqlRouterEngine sqlRouterEngine) {
        this.logicSql = logicSql;
        this.sqlRouterEngine = sqlRouterEngine;
    }

    public SqlRouterResult route(final List<Object> parameters) {
        if (sqlParserResult == null) {
            sqlParserResult = sqlRouterEngine.parserSql(logicSql, parameters);
        } else {
            for (ConditionContext each : sqlParserResult.getConditionContexts()) {
                each.setNewConditionValue(parameters);
            }
        }
        return sqlRouterEngine.routeSql(sqlParserResult, parameters);
    }
}

