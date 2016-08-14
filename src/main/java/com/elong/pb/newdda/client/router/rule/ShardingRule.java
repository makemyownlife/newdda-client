package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.constants.DatabaseType;

import java.util.Collection;

/**
 * 分区规则
 * Created by zhangyong on 2016/7/26.
 */
public class ShardingRule {

    private Collection<String> shardingColumns;

    public Collection<String> getShardingColumns() {
        return shardingColumns;
    }

    private DatabaseType databaseType;

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
    
}
