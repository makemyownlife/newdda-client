package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.datasource.DataSourceContainer;

import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 分区规则
 * Created by zhangyong on 2016/7/26.
 */
public class ShardingRule {

    //默认mysql数据类型也可以配置
    private DatabaseType databaseType = DatabaseType.MySQL;

    private DataSourceContainer dataSourceContainer;

    private Algorithm algorithm;

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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public DataSourceContainer getDataSourceContainer() {
        return dataSourceContainer;
    }

    public void setDataSourceContainer(DataSourceContainer dataSourceContainer) {
        this.dataSourceContainer = dataSourceContainer;
    }

    //======================================================================get  set method end =======================================================================

}
