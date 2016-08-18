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

    private List<Object> shardingColumns;

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
    //======================================================================get  set method end =======================================================================

}
