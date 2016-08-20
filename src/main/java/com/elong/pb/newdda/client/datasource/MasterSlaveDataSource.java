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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSource> getSlaveDataSources() {
        return slaveDataSources;
    }

    public void setSlaveDataSources(List<DataSource> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }

}
