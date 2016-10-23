package com.elong.pb.newdda.client.router.rule;

import java.util.List;

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
    private List<String> targetTableNames;

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

    public List<String> getTargetTableNames() {
        return targetTableNames;
    }

    public void setTargetTableNames(List<String> targetTableNames) {
        this.targetTableNames = targetTableNames;
    }

}
