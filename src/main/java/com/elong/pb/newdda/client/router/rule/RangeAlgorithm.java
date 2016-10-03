package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.exception.ShardingJdbcException;
import com.elong.pb.newdda.client.router.action.ShardingValue;
import com.elong.pb.newdda.client.util.StringUtil;
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

    private static final int PARTITION_LENGTH = 1024;

    // %转换为&操作的换算数值
    private static final long AND_VALUE = PARTITION_LENGTH - 1;

    //================================================================set  set method  start ====================================================================
    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    @Override
    public AlgorithmResult doAlgorithm(ShardingValue shardingValue) {
        if(dataSourceList.size() != rangeList.size()) {
            throw new ShardingJdbcException("doAlgorithm error , 数据源数目与分区配置不一致!");
        }
        //将所有的参数解析成string 用string来hash
        String valueStr = null;
        Object obj = shardingValue.getValue();
        if(obj instanceof Number ) {
            valueStr = String.valueOf(obj);
        }
        if(obj instanceof String) {
            valueStr = (String)obj;
        }

        //计算hash值
        long hash = StringUtil.hash(valueStr , 0 , valueStr.length());

        //目标数据源
        String targetDataSource = null;
        //在虚拟的1024个分区中 查看位于哪个分区中
        int temp  = (int) (hash & AND_VALUE);
        for(int i = 0 ;i < rangeList.size() ; i++) {
            String range = rangeList.get(i);
            String[] arr = range.split(",");
            int start = Integer.valueOf(arr[0]);
            int end = Integer.valueOf(arr[1]);
            if(temp >= start && temp <= end) {
                targetDataSource = dataSourceList.get(i);
                break;
            }
        }

        AlgorithmResult algorithmResult = new AlgorithmResult();
        algorithmResult.setTargetDataSource(targetDataSource);
        return algorithmResult;
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
