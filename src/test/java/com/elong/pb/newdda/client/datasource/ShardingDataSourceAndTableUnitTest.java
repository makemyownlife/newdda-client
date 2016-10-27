package com.elong.pb.newdda.client.datasource;

import com.elong.pb.newdda.client.router.rule.ShardingRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * 支持分片的数据源 相关测试
 * User: zhangyong
 * Date: 2016/3/29
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"/spring/spring-config-sharding-datasource2.xml"})
public class ShardingDataSourceAndTableUnitTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ShardingDataSource shardingDataSource;

    @Autowired
    private ShardingRule shardingRule;

    @Test
    public void testInsertStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        Statement statement = null;
        String uuid = "'" + UUID.randomUUID().toString() + "'";
        String sql = "INSERT INTO `t_coupon`(id , member_id ,coupon_value, create_time ,update_time) VALUES (" + uuid + ", 302919, 12.2, now(), now())";
        try {
            statement = null;
            statement = shardingConnection.createStatement();
            int updateRow = statement.executeUpdate(sql);
            System.out.println("updateRow=" + updateRow);
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
    public void testSelectStatement() throws SQLException {
        Connection shardingConnection = shardingDataSource.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        String sql = "select * from t_coupon where  member_id  = 302919";
        try {
            statement = null;
            rs = null;
            statement = shardingConnection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println("结果是:" + rs.getString("id") + " coupon:" + rs.getBigDecimal("coupon_value"));
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
