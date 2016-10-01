package com.elong.pb.newdda.client.router.rule;

/**
 * 算法结果
 * Created by zhangyong on 16/10/1.
 */
public class AlgorithmResult {

    //命中数据源
    private String targetDataSource;

    //逻辑表名
    private String logicTableName;

    //替换后的表名
    private String replaceTableName;

    public String getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(String targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public void setLogicTableName(String logicTableName) {
        this.logicTableName = logicTableName;
    }

    public String getReplaceTableName() {
        return replaceTableName;
    }

    public void setReplaceTableName(String replaceTableName) {
        this.replaceTableName = replaceTableName;
    }

}
