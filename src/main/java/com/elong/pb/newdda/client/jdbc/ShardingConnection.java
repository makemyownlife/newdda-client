package com.elong.pb.newdda.client.jdbc;

import com.elong.pb.newdda.client.jdbc.adapter.AbstractConnectionAdapter;
import com.elong.pb.newdda.client.router.rule.ShardingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collection;

/**
 * 分区的链接
 * User: zhangyong
 * Date: 2016/3/31
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class ShardingConnection extends AbstractConnectionAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ShardingConnection.class);

    private ShardingRule shardingRule;

    public ShardingConnection(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
    }

    @Override
    protected Collection<Connection> getConnections() {
        return null;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

}