package com.elong.pb.newdda.client.parser;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;
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

    @Test
    public void testMultipleTableSelect() throws Exception {
        String sql = "select a.id as userId , b.id as orderId from user a , order b  where a.id = b.user_id and a.id = 12";

        SQLStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> stmtList = parser.parseStatementList();
        Assert.assertEquals(1, stmtList.size());
    }

    @Test
    public void testMultipleTableUnionSelect() throws Exception {
        String sql = "(select id from t1) union all (select id from t2) union all (select id from t3) ordeR By d desC limit 1 offset ?";
        MySqlStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement stmt = parser.parseStatementList().get(0);
        parser.match(Token.EOF);
        String output = SQLUtils.toMySqlString(stmt);
        Assert.assertEquals("SELECT id\n" + //
                "FROM t1\n" + //
                "UNION ALL\n" + //
                "SELECT id\n" + //
                "FROM t2\n" + //
                "UNION ALL\n" + //
                "(SELECT id\n" + //
                "FROM t3)\n" + //
                "ORDER BY d DESC\n" + //
                "LIMIT ?, 1", output);
    }



}
