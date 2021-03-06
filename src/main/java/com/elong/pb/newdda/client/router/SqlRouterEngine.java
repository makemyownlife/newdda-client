package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.exception.ShardingJdbcException;
import com.elong.pb.newdda.client.exception.SqlParserException;
import com.elong.pb.newdda.client.router.action.DefaultShardingAction;
import com.elong.pb.newdda.client.router.action.ShardingAction;
import com.elong.pb.newdda.client.router.parser.SqlParserEngine;
import com.elong.pb.newdda.client.router.parser.SqlParserFactory;
import com.elong.pb.newdda.client.router.parser.SqlParserResult;
import com.elong.pb.newdda.client.router.result.merge.MergeContext;
import com.elong.pb.newdda.client.router.result.router.*;
import com.elong.pb.newdda.client.router.rule.ShardingRule;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * sql语句路由相关的内容
 * Created by zhangyong on 2016/7/27.
 */
public class SqlRouterEngine {

    private final static Logger logger = LoggerFactory.getLogger(SqlRouterEngine.class);

    private DatabaseType databaseType;

    private final ShardingRule shardingRule;

    public SqlRouterEngine(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
        this.databaseType = shardingRule.getDatabaseType();
    }

    public SqlRouterResult route(final String logicSql) throws SqlParserException {
        return route(logicSql, Collections.emptyList());
    }

    public SqlRouterResult route(final String logicSql, final List<Object> parameters) throws SqlParserException {
        SqlParserResult sqlParserResult = parserSql(logicSql, parameters);
        return routeSql(sqlParserResult, parameters);
    }

    public SqlParserResult parserSql(final String logicSql, final List<Object> parameters) {
        SqlParserEngine sqlParserEngine = SqlParserFactory.createParserEngine(databaseType, logicSql, parameters);
        SqlParserResult sqlParserResult = sqlParserEngine.parse();
        return sqlParserResult;
    }

    public SqlRouterResult routeSql(final SqlParserResult sqlParserResult, final List<Object> parameters) {
        RouterContext routerContext = sqlParserResult.getRouteContext();
        MergeContext mergeContext = sqlParserResult.getMergeContext();
        List<ConditionContext> conditionContextList = sqlParserResult.getConditionContexts();

        SqlStatementType sqlStatementType = routerContext.getSqlStatementType();
        SqlAppender sqlAppender = routerContext.getSqlAppender();

        SqlRouterResult sqlRouterResult = new SqlRouterResult(sqlStatementType, mergeContext);

        for (ConditionContext conditionContext : conditionContextList) {
            Collection<RouterTable> routerTables = routerContext.getRouterTables();
            //通过google util 得到逻辑列表
            Set<String> logicTables = Sets.newLinkedHashSet(Collections2.transform(routerTables, new Function<RouterTable, String>() {
                @Override
                public String apply(final RouterTable input) {
                    return input.getName();
                }
            }));
            //路由sql得到执行节点
            Collection<SqlExecutionUnit> eachResult = routeSQL(
                    conditionContext,
                    logicTables,
                    sqlAppender,
                    sqlStatementType
            );
            if (eachResult != null && !eachResult.isEmpty()) {
                sqlRouterResult.getExecutionUnits().addAll(eachResult);
            }
        }

        processLimit(sqlRouterResult.getExecutionUnits(), sqlParserResult, parameters);

        if (sqlRouterResult.getExecutionUnits().isEmpty()) {
            throw new ShardingJdbcException("Sharding-JDBC: cannot route any result, please check your sharding rule.");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getExecutionUnits:" + sqlRouterResult.getExecutionUnits());
        }

        return sqlRouterResult;
    }

    private Collection<SqlExecutionUnit> routeSQL(final ConditionContext conditionContext, final Set<String> logicTables, final SqlAppender sqlAppender, final SqlStatementType sqlStatementType) {
        ShardingAction shardingAction = new DefaultShardingAction(
                shardingRule,
                conditionContext,
                logicTables,
                sqlAppender,
                sqlStatementType
        );
        return shardingAction.doSharding();
    }

    private void processLimit(final Set<SqlExecutionUnit> executionUnits, final SqlParserResult parsedResult, final List<Object> parameters) {
        // TODO: 第二版处理 limit 相关的内容
    }

    //=========================================================== prepare start ==========================================================================

    public PreparedSqlRouter prepareSql(String logicSql) {
        return new PreparedSqlRouter(logicSql, this);
    }

}
