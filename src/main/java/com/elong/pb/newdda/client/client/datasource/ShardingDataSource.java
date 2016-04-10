package com.elong.pb.newdda.client.client.datasource;

import com.elong.pb.newdda.client.client.jdbc.adapter.AbstractDataSourceAdapter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 支持分片的数据源 (后续可以扩展到)
 * User: zhangyong
 * Date: 2016/3/29
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
public class ShardingDataSource extends AbstractDataSourceAdapter {

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

}
