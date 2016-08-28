package com.elong.pb.newdda.client.router.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 分区算法
 * Created by zhangyong on 2016/8/20.
 */
public class ShardingRangeAlgorithm implements ShardingAlgorithm {

    private final static Logger logger = LoggerFactory.getLogger(ShardingRangeAlgorithm.class);

    //数据源列表
    private List<String> dataSourceList;

    //分区列表
    private List<String> rangeList;

    //================================================================set  set method  start ====================================================================
    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    public void setDataSourceList(List<String> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    public List<String> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<String> rangeList) {
        this.rangeList = rangeList;
    }
    //================================================================set  set method  end ====================================================================

}