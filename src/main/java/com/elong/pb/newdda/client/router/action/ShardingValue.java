package com.elong.pb.newdda.client.router.action;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by zhangyong on 16/9/29.
 */
public class ShardingValue<T extends Comparable<?>>  {

    private final String logicTableName;

    private final String columnName;

    private final T value;

    private final Collection<T> values;


    public ShardingValue(final String logicTableName, final String columnName, final T value , Collection<T> values) {
        this.logicTableName = logicTableName;
        this.columnName = columnName;
        this.value = value;
        this.values = values;
    }

    public ShardingValue(final String logicTableName, final String columnName, final T value) {
        this(logicTableName, columnName, value, Collections.EMPTY_LIST);
    }

    public ShardingValue(final String logicTableName, final String columnName, final Collection<T> values) {
        this(logicTableName, columnName, null, values);
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

    public Collection<T> getValues() {
        return values;
    }

    //=========================================================================== get method end  ===========================================================================

}
