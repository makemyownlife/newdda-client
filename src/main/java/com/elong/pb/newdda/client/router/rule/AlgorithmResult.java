package com.elong.pb.newdda.client.router.rule;

/**
 * 算法结果
 * Created by zhangyong on 16/10/1.
 */
public class AlgorithmResult {

    //命中数据源
    private String targetDataSource;

    //逻辑表名
    private String logicTable;

    //目标表名
    private String targetTableName;

    public String getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(String targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    public String getLogicTable() {
        return logicTable;
    }

    public void setLogicTable(String logicTable) {
        this.logicTable = logicTable;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

}
