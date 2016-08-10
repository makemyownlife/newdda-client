package com.elong.pb.newdda.client.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * select statement的描述
 * Created by zhangyong on 2016/8/3.
 */
public class SelectParserUnitTest {

    @Test
    public void testSelect() throws Exception {
        String sql = "select * from person where name = 'xx' AND gender = 'M'";

        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        Assert.assertEquals(1, stmtList.size());

    }

}
