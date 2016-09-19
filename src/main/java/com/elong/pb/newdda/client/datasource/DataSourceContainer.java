package com.elong.pb.newdda.client.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源容器
 * Created by zhangyong on 2016/9/19.
 */
public class DataSourceContainer {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceContainer.class);

    //所有的数据源
    private final Map<String, MasterSlaveDataSource> container = new HashMap<String, MasterSlaveDataSource>();

}
