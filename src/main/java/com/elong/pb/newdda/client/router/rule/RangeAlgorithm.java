package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.router.action.ShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 分区算法
 * Created by zhangyong on 2016/8/20.
 */
public class RangeAlgorithm implements Algorithm {

    private final static Logger logger = LoggerFactory.getLogger(RangeAlgorithm.class);

    //数据源列表
    private List<String> dataSourceList;

    //分区列表
    private List<String> rangeList;

    //================================================================set  set method  start ====================================================================
    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    @Override
    public AlgorithmResult doAlgorithm(ShardingValue shardingValue) {
        return null;
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
