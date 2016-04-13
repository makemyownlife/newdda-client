package com.elong.pb.newdda.client.datasource;

import javax.sql.DataSource;
import java.util.List;

/**
 * 主从数据源
 * User: zhangyong
 * Date: 2016/4/12
 * Time: 21:03
 */
public class MasterSlaveDataSource {

    private String name;

    private DataSource masterDataSource;

    private List<DataSource> slaveDataSources;

    public MasterSlaveDataSource(String name, DataSource masterDataSource, List<DataSource> slaveDataSources) {
        this.name = name;
        this.masterDataSource = masterDataSource;
        this.slaveDataSources = slaveDataSources;
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public String getName() {
        return name;
    }

    public List<DataSource> getSlaveDataSources() {
        return slaveDataSources;
    }

}
