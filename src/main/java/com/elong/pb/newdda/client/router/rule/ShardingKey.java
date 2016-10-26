package com.elong.pb.newdda.client.router.rule;

/**
 * Created by zhangyong on 2016/8/20.
 */
public class ShardingKey {

    //表名
    private String tableName;

    //分区字段
    private String shardingColumn;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getShardingColumn() {
        return shardingColumn;
    }

    public void setShardingColumn(String shardingColumn) {
        this.shardingColumn = shardingColumn;
    }

}
