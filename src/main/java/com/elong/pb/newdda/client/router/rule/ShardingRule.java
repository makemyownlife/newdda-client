package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.constants.DatabaseType;

import java.util.List;

/**
 * 分区规则
 * Created by zhangyong on 2016/7/26.
 */
public class ShardingRule {

    //默认mysql数据类型也可以配置
    private DatabaseType databaseType = DatabaseType.MySQL;

    private List<String> shardingColumns;

    public List<String> getShardingColumns() {
        return shardingColumns;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

}
