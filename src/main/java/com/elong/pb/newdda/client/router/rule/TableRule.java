package com.elong.pb.newdda.client.router.rule;

/**
 * Created by zhangyong on 2016/8/20.
 */
public class TableRule {

    private String tableName;

    private String shardingKey;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public void setShardingKey(String shardingKey) {
        this.shardingKey = shardingKey;
    }

}
