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

    //默认mysql数据类型也可以配置
    private DatabaseType databaseType = DatabaseType.MySQL;

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

}
