package com.elong.pb.newdda.client.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源容器
 * Created by zhangyong on 2016/9/19.
 */
public class DataSourceContainer {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceContainer.class);

    //主从数据源列表
    private List<MasterSlaveDataSource> masterSlaveDataSourceList = new ArrayList<MasterSlaveDataSource>();

    //所有的数据源
    private final Map<String, MasterSlaveDataSource> container = new HashMap<String, MasterSlaveDataSource>();

    //===============================================================get set method start =========================================================================
    public List<MasterSlaveDataSource> getMasterSlaveDataSourceList() {
        return masterSlaveDataSourceList;
    }
    public void setMasterSlaveDataSourceList(List<MasterSlaveDataSource> masterSlaveDataSourceList) {
        this.masterSlaveDataSourceList = masterSlaveDataSourceList;
    }
    //===============================================================get set method end =========================================================================

}
