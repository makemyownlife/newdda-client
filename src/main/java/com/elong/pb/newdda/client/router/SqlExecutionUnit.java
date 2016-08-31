package com.elong.pb.newdda.client.router;

/**
 * 最小的执行单元
 * Created by zhangyong on 16/8/31.
 */
public class SqlExecutionUnit {

    private final String dataSource;

    private final String sql;

    public SqlExecutionUnit(final String dataSource, final String sql) {
        this.dataSource = dataSource;
        this.sql = sql;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("route sql to db: [").append(dataSource).append("] sql:[").append(sql).append("]");
        return builder.toString();
    }

}
