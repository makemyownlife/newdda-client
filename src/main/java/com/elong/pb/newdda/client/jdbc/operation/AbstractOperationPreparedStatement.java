package com.elong.pb.newdda.client.jdbc.operation;

import com.elong.pb.newdda.client.jdbc.ShardingConnection;
import com.elong.pb.newdda.client.jdbc.ShardingStatement;
import com.elong.pb.newdda.client.router.SqlRouterEngine;

import java.io.Reader;
import java.sql.*;

/**
 * Created by zhangyong on 2016/7/26.
 */
public abstract class AbstractOperationPreparedStatement extends ShardingStatement implements PreparedStatement {

    public AbstractOperationPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        super(shardingConnection, sqlRouterEngine, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public final ResultSetMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMetaData");
    }

    @Override
    public final ParameterMetaData getParameterMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("ParameterMetaData");
    }

    @Override
    public final void setNString(final int parameterIndex, final String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNString");
    }

    @Override
    public final void setNClob(final int parameterIndex, final NClob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNClob");
    }

    @Override
    public final void setNClob(final int parameterIndex, final Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNClob");
    }

    @Override
    public final void setNClob(final int parameterIndex, final Reader x, final long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNClob");
    }

    @Override
    public final void setNCharacterStream(final int parameterIndex, final Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNCharacterStream");
    }

    @Override
    public final void setNCharacterStream(final int parameterIndex, final Reader x, final long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("setNCharacterStream");
    }

    @Override
    public final void setArray(final int parameterIndex, final Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setArray");
    }

    @Override
    public final void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        throw new SQLFeatureNotSupportedException("setRowId");
    }

}
