package com.elong.pb.newdda.client.datasource;

import com.elong.pb.newdda.client.jdbc.ShardingConnection;
import com.elong.pb.newdda.client.jdbc.adapter.AbstractDataSourceAdapter;
import com.elong.pb.newdda.client.router.rule.ShardingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger logger = LoggerFactory.getLogger(ShardingDataSource.class);

    private ShardingRule shardingRule;

    public ShardingDataSource(ShardingRule shardingRule) {
        this.shardingRule = shardingRule;
    }

    @Override
    public Connection getConnection() throws SQLException {
        ShardingConnection shardingConnection = new ShardingConnection(shardingRule);
        return shardingConnection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

}
