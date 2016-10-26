package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.router.action.ShardingValue;
import com.elong.pb.newdda.client.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分区算法
 * Created by zhangyong on 2016/8/20.
 */
public class RangeAlgorithm implements Algorithm {

    private final static Logger logger = LoggerFactory.getLogger(RangeAlgorithm.class);

    public DataSourceRule dataSourceRule;

    public TableRule tableRule;

    public AlgorithmResult doAlgorithm(ShardingValue shardingValue) {
        Object obj = shardingValue.getValue();
        String valueStr = null;
        if (obj instanceof Number) {
            valueStr = String.valueOf(obj);
        } else if (obj instanceof String) {
            valueStr = (String) obj;
        } else {
            valueStr = String.valueOf(obj);
        }
        long hash = StringUtil.hash(valueStr, 0, valueStr.length());
        String targetDataSource = dataSourceRule.calculate(hash);
        String targetTableName = (tableRule != null) ? tableRule.calculate(shardingValue.getLogicTableName(), hash) : shardingValue.getLogicTableName();

        AlgorithmResult algorithmResult = new AlgorithmResult();
        algorithmResult.setLogicTable(shardingValue.getLogicTableName());
        algorithmResult.setTargetTableName(targetTableName);
        algorithmResult.setTargetDataSource(targetDataSource);
        return algorithmResult;
    }

    //================================================================set  set method  start ====================================================================
    public void setDataSourceRule(DataSourceRule dataSourceRule) {
        this.dataSourceRule = dataSourceRule;
    }

    public void setTableRule(TableRule tableRule) {
        this.tableRule = tableRule;
    }
    //================================================================set  set method  end ====================================================================

}
