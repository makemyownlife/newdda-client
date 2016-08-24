package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.constants.DatabaseType;

import java.util.ArrayList;
import java.util.List;

/**
 * 分区规则
 * Created by zhangyong on 2016/7/26.
 */
public class ShardingRule {

    //默认mysql数据类型也可以配置
    private DatabaseType databaseType = DatabaseType.MySQL;

    private ShardingAlgorithm shardingAlgorithm;

    private List<TableRule> tableRules;

    private List<Object> shardingColumns = new ArrayList<Object>();

    public List<Object> getShardingColumns() {
        return shardingColumns;
    }

    //======================================================================get  set method start ======================================================================
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public List<TableRule> getTableRules() {
        return tableRules;
    }

    public void setTableRules(List<TableRule> tableRules) {
        this.tableRules = tableRules;
    }

    public ShardingAlgorithm getShardingAlgorithm() {
        return shardingAlgorithm;
    }

    public void setShardingAlgorithm(ShardingAlgorithm shardingAlgorithm) {
        this.shardingAlgorithm = shardingAlgorithm;
    }

//======================================================================get  set method end =======================================================================

}
