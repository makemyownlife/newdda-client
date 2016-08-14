package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.exception.SqlParserException;
import com.elong.pb.newdda.client.router.parser.SqlParserEngine;
import com.elong.pb.newdda.client.router.parser.SqlParserFactory;
import com.elong.pb.newdda.client.router.rule.ShardingRule;

import java.util.Collections;
import java.util.List;

/**
 * sql语句路由相关的内容
 * Created by zhangyong on 2016/7/27.
 */
public class SqlRouterEngine {

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
        SqlParserEngine sqlParserEngine = SqlParserFactory.createParserEngine(databaseType, logicSql, parameters, shardingRule.getShardingColumns());
        return null;
    }


}
