package com.elong.pb.newdda.client.router;

import com.elong.pb.newdda.client.router.result.router.SqlAppender;

/**
 * 最小的执行单元
 * Created by zhangyong on 16/8/31.
 */
public class SqlExecutionUnit {

    private final String dataSource;

    private String sql;

    private SqlAppender sqlAppender;

    public SqlExecutionUnit(final String dataSource, final SqlAppender sqlAppender) {
        this.dataSource = dataSource;
        this.sqlAppender = sqlAppender.cloneBuilder();
        this.sql = sqlAppender.toSQL();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.dataSource == null ? 0 : this.dataSource.hashCode());
        result = prime * result + (this.sql == null ? 0 : this.sql.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("route sql to db: [").append(dataSource).append("] sql:[").append(sql).append("]");
        return builder.toString();
    }

    //====================================================set get method start ====================================================

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDataSource() {
        return dataSource;
    }

    //====================================================set get method end  =====================================================

}
