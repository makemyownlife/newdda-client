package com.elong.pb.newdda.client.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

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
        shardingDataSource.getConnection();
    }

}
