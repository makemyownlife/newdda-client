package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.exception.SQLParserException;
import com.elong.pb.newdda.client.router.rule.ShardingRule;

import java.util.List;

/**
 * sql语句路由相关的内容
 * Created by zhangyong on 2016/7/27.
 */
public class SqlRouterEngine {

    private final ShardingRule shardingRule;

    public SqlRouterEngine(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
    }

    public SqlRouterResult route(final String logicSql, final List<Object> parameters) throws SQLParserException {
        return null;
    }

}
