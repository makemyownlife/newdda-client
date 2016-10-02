package com.elong.pb.newdda.client.router.action;

/**
 * Created by zhangyong on 16/9/29.
 */
public class ShardingValue<T extends Comparable<?>>  {

    private final String logicTableName;

    private final String columnName;

    private final T value;

    public ShardingValue(final String logicTableName, final String columnName, final T value) {
        this.logicTableName = logicTableName;
        this.columnName = columnName;
        this.value = value;
    }

    //=========================================================================== get method start ===========================================================================

    public String getLogicTableName() {
        return logicTableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public T getValue() {
        return value;
    }

    //=========================================================================== get method end  ===========================================================================

}
