package com.elong.pb.newdda.client.datasource;

import com.elong.pb.newdda.client.client.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

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
    public void testShardDataSource(){
        System.out.println(shardingDataSource);
    }

}
