package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.rule.ShardingRule;

/**
 * sql语句路由结果
 * Created by zhangyong on 2016/7/30.
 */
public class SqlRouterResult {

    private final ShardingRule shardingRule;

    public SqlRouterResult(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
    }

}
