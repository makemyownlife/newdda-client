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

    //查询不到路由到所有的数据库或者表
    private boolean selectNoneRouteAll = false;

    private DataSourceContainer dataSourceContainer;

    private Algorithm algorithm;

    private List<TableRule> tableRules;

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

    public boolean isSelectNoneRouteAll() {
        return selectNoneRouteAll;
    }

    public void setSelectNoneRouteAll(boolean selectNoneRouteAll) {
        this.selectNoneRouteAll = selectNoneRouteAll;
    }


//======================================================================get  set method end =======================================================================

}
