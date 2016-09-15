package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.result.router.SqlAppender;

/**
 * 最小的执行单元
 * Created by zhangyong on 16/8/31.
 */
public class SqlExecutionUnit {

    private final String dataSource;

    private final SqlAppender sqlAppender;

    public SqlExecutionUnit(final String dataSource, final SqlAppender sqlAppender) {
        this.dataSource = dataSource;
        this.sqlAppender = sqlAppender;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("route sql to db: [").append(dataSource).append("] sql:[").append(sqlAppender).append("]");
        return builder.toString();
    }

}
