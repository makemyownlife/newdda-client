package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.rule.ShardingRule;

/**
 * sql语句路由相关的内容
 * Created by zhangyong on 2016/7/27.
 */
public class SqlRouter {

    private final ShardingRule shardingRule;

    public SqlRouter(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
    }

}
