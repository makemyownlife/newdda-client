package com.elong.pb.newdda.client.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
