package com.elong.pb.newdda.client.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据源容器
 * Created by zhangyong on 2016/9/19.
 */
public class DataSourceContainer {

    //所有的数据源
    private Map<String, MasterSlaveDataSource> container;

    public Map<String, MasterSlaveDataSource> getContainer() {
        if (container == null) {
            synchronized (DataSourceContainer.class) {
                container = new HashMap<String, MasterSlaveDataSource>();
                for (MasterSlaveDataSource masterSlaveDataSource : masterSlaveDataSourceList) {
                    container.put(masterSlaveDataSource.getName(), masterSlaveDataSource);
                }
            }
        }
        return container;
    }

    //主从数据源列表
    private List<MasterSlaveDataSource> masterSlaveDataSourceList = new ArrayList<MasterSlaveDataSource>();

    //===============================================================get set method start =========================================================================
    public List<MasterSlaveDataSource> getMasterSlaveDataSourceList() {
        return masterSlaveDataSourceList;
    }

    public void setMasterSlaveDataSourceList(List<MasterSlaveDataSource> masterSlaveDataSourceList) {
        this.masterSlaveDataSourceList = masterSlaveDataSourceList;
    }
    //===============================================================get set method end =========================================================================

}
