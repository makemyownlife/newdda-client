package com.elong.pb.newdda.plugin;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * mybatis 单元测试
 * User: zhangyong
 * Date: 2016/3/25
 * Time: 21:01
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"/spring/spring-config.xml"})
public class MybatisUnitTest  extends AbstractTransactionalTestNGSpringContextTests {

    @Test
    public void testQuery() {

    }

}
