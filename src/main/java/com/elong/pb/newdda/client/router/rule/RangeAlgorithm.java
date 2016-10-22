package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.constants.ShardingConstants;
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

    //表分区列表
    private static final int PARTITION_LENGTH = ShardingConstants.SHARDING_LENGTH;

    // %转换为&操作的换算数值
    private static final long AND_VALUE = PARTITION_LENGTH - 1;

    //数据源列表
    private List<String> dataSourceList;

    //数据源分区列表
    private List<String> dataSourceRangeList;

    //表分区
    private List<String> tableRangeList;

    //================================================================set  set method  start ====================================================================
    public List<String> getDataSourceList() {
        return dataSourceList;
    }

    @Override
    public AlgorithmResult doAlgorithm(ShardingValue shardingValue) {
        //分区到多个数据源的情况下, 分区范围也必须有多个
        if(dataSourceList.size() > 1 && (dataSourceList.size() != dataSourceRangeList.size())) {
            throw new ShardingJdbcException("doAlgorithm error,数据源数目与分区配置不一致!分区到多个数据源的情况下, 分区范围也必须有多个");
        }
        //将所有的参数解析成string 用string来hash
        String valueStr = null;
        Object obj = shardingValue.getValue();
        if(obj instanceof Number ) {
            valueStr = String.valueOf(obj);
        } else if(obj instanceof String) {
            valueStr = (String)obj;
        } else {
            //这么写会有一定的风险
            valueStr = String.valueOf(obj);
        }
        //计算hash值
        long hash = StringUtil.hash(valueStr , 0 , valueStr.length());
        int realPos  = (int) (hash & AND_VALUE);

        AlgorithmResult algorithmResult = new AlgorithmResult();

        //目标数据源 若仅有一个库,则命中当前唯一的库,否则计算hash值
        String targetDataSource = null;
        if(dataSourceList.size() == 1) {
            targetDataSource = dataSourceList.get(0);
        } else {
            //在虚拟的1024个分区中 查看位于哪个数据源分区中
            for (int i = 0; i < dataSourceRangeList.size(); i++) {
                String range = dataSourceRangeList.get(i);
                String[] arr = range.split(",");
                int start = Integer.valueOf(arr[0]);
                int end = Integer.valueOf(arr[1]);
                if (realPos >= start && realPos <= end) {
                    targetDataSource = dataSourceList.get(i);
                    break;
                }
            }
        }
        algorithmResult.setTargetDataSource(targetDataSource);

        String targetTableName = null;
        //计算表分区
        if(tableRangeList != null) {
            int tableRangeSize = tableRangeList.size();
            for(int index = 0 ; index < tableRangeSize ; index++) {
                String range = tableRangeList.get(index);
                String[] arr = range.split(",");
                int start = Integer.valueOf(arr[0]);
                int end = Integer.valueOf(arr[1]);
                if (realPos >= start && realPos <= end) {
                    String prefix = "";
                    if(index >= 0 && index < 10) {
                        prefix = "000";
                    }
                    if(index >= 10 && index < 100) {
                        prefix = "00";
                    }
                    if(index >= 100 && index < 1000) {
                        prefix = "0";
                    }
                    targetTableName = shardingValue.getLogicTableName() + "_" + prefix + index;
                    break;
                }
            }
        }
        algorithmResult.setTargetTableName(targetTableName == null ? shardingValue.getLogicTableName() : targetTableName);
        return algorithmResult;
    }

    public void setDataSourceList(List<String> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    public List<String> getDataSourceRangeList() {
        return dataSourceRangeList;
    }

    public void setDataSourceRangeList(List<String> dataSourceRangeList) {
        this.dataSourceRangeList = dataSourceRangeList;
    }

    public List<String> getTableRangeList() {
        return tableRangeList;
    }

    public void setTableRangeList(List<String> tableRangeList) {
        this.tableRangeList = tableRangeList;
    }

    //================================================================set  set method  end ====================================================================

}
