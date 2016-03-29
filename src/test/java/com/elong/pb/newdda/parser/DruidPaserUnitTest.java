package com.elong.pb.newdda.parser;

import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcUtils;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/25
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class DruidPaserUnitTest {

    @Test
    public void testSelectStatement() {
        String sql = "select a.*  from user a , city c where id = 1 and a.city_id = c.id ";
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
        List stmtList = parser.parseStatementList();
    }

    @Test
    public void testInsertStatement() {

    }

    @Test
    public void testUpdateStatement() {

    }

}
