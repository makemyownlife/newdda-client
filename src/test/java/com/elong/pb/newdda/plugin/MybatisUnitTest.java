package com.elong.pb.newdda.plugin;

import com.elong.pb.newdda.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * mybatis 单元测试
 * User: zhangyong
 * Date: 2016/3/25
 * Time: 21:01
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"/spring/spring-config.xml"})
public class MybatisUnitTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TestDao testDao;

    @Test
    public void testQuery() {
        testDao.query();
    }

}
