package com.elong.pb.newdda.jdbc.adapter;

import com.dangdang.ddframe.rdb.sharding.exception.ShardingJdbcException;
import com.dangdang.ddframe.rdb.sharding.jdbc.util.JdbcMethodInvocation;

import java.sql.SQLException;
import java.sql.Wrapper;

/**
 * 适配器封装
 * User: zhangyong
 * Date: 2016/3/31
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class WrapperAdapter implements Wrapper {

    @SuppressWarnings("unchecked")
    @Override
    public final <T> T unwrap(final Class<T> iface) throws SQLException {
        if (isWrapperFor(iface)) {
            return (T) this;
        }
        throw new SQLException(String.format("[%s] cannot be unwrapped as [%s]", getClass().getName(), iface.getName()));
    }

    @Override
    public final boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

}
