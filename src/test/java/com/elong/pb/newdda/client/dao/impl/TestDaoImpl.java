package com.elong.pb.newdda.client.dao.impl;

import com.elong.pb.newdda.client.dao.TestDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/26
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */
public class TestDaoImpl extends SqlSessionDaoSupport implements TestDao {

    public List query() {
        return getSqlSession().selectList("test.query" ,1);
    }

}
