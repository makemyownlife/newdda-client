package com.elong.pb.newdda.client.router.result.router;

/**
 * Created by zhangyong on 2016/9/7.
 */
public class RouterColumn {

    private final String columnName;

    private final String tableName;

    public RouterColumn(String columnName, String tableName) {
        this.columnName = columnName;
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.columnName == null ? 0 : this.columnName.hashCode());
        result = prime * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        return result;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("columnName:").append(columnName).append(" tableName:").append(tableName);
        return sb.toString();
    }

}
