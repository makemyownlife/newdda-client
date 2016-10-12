package com.elong.pb.newdda.client.datasource;

import com.elong.pb.newdda.client.router.rule.ShardingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.sql.*;

/**
 * 支持分片的数据源 相关测试
 * User: zhangyong
 * Date: 2016/3/29
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"/spring/spring-config-sharding-datasource.xml"})
public class ShardingDataSourceUnitTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ShardingDataSource shardingDataSource;

    @Autowired
    private ShardingRule shardingRule;

    @Test
    public void testShardDataSource() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = null;
            rs = null;
            statement = shardingConnection.prepareStatement("select * from test where id = ?");
            statement.setInt(1, 1);
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("结果是:" + rs.getString("id"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (shardingConnection != null) {
                shardingConnection.close();
            }
        }
    }

    @Test
    public void testStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "select * from test where user_id = 1 and user_name = 'zhangyong'";
        try {
            statement = null;
            rs = null;
            statement = shardingConnection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("结果是:" + rs.getString("user_name"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (shardingConnection != null) {
                shardingConnection.close();
            }
        }
    }

    @Test
    public void testUpdateStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        Statement statement = null;
        String sql = "update test set user_name = 'zhangyong04' where user_id = 1";
        try {
            statement = null;
            statement = shardingConnection.createStatement();
            int updateRow = statement.executeUpdate(sql);
            System.out.println(updateRow);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (shardingConnection != null) {
                shardingConnection.close();
            }
        }
    }

    @Test
    public void testSelectAllStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "select * from test";
        try {
            statement = null;
            rs = null;
            statement = shardingConnection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("结果是:" + rs.getString("user_name"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (shardingConnection != null) {
                shardingConnection.close();
            }
        }
    }

    @Test
    public void testPrepareStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from test where user_id = ? ";
        try {
            statement = null;
            rs = null;
            statement = shardingConnection.prepareStatement(sql);
            statement.setInt(1, 1);
            rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("结果是:" + rs.getString("user_name"));
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (shardingConnection != null) {
                shardingConnection.close();
            }
        }
    }

}
