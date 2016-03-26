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
        String sql = "select *  from user where id = 1";
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
        List stmtList = parser.parseStatementList();
    }

}
