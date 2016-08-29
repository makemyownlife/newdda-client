package com.elong.pb.newdda.client.visitor;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlSelectVisitor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * mysql查询单元测试
 * Created by zhangyong on 2016/8/15.
 */
public class MySqlSelectVisitorUnitTest {

    @Test
    public void shardkeyTest() {
        String sql = "select a.member_name , a.member_id , b.destination , b.start_place  FROM t_scd_order a , t_scd_order_detail b  where a.id = b.order_id and a.id = 1";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);

        MySqlSelectVisitor mySqlSelectVisitor = new MySqlSelectVisitor();
        statement.accept(mySqlSelectVisitor);

        String output = SQLUtils.toMySqlString(statement);
        Assert.assertNotNull(output);

    }

    @Test
    public void shardkeyTestIn() {
        String sql = "select a.member_name , a.member_id , b.destination , b.start_place  FROM t_scd_order a , t_scd_order_detail b  where a.id = b.order_id and a.id in (1,2,3,4)";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);

        MySqlSelectVisitor mySqlSelectVisitor = new MySqlSelectVisitor();
        statement.accept(mySqlSelectVisitor);

        String output = SQLUtils.toMySqlString(statement);
        Assert.assertNotNull(output);
    }

    @Test
    public void shardkeyTestInAndEqual() {
        String sql = "select a.member_name , a.member_id , b.destination , b.start_place  FROM t_scd_order a , t_scd_order_detail b , t_scd_order_finance c  where a.id = b.order_id and a.id in (1,2,3,4) and b.start_place = 'shanghai'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);

        MySqlSelectVisitor mySqlSelectVisitor = new MySqlSelectVisitor();
        statement.accept(mySqlSelectVisitor);

        String output = SQLUtils.toMySqlString(statement);
        Assert.assertNotNull(output);
    }

    @Test
    public void shardKeySingleTable() {
        String sql = "select amember_name , member_id  FROM t_scd_order  where start_place = 'shanghai'";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);

        MySqlSelectVisitor mySqlSelectVisitor = new MySqlSelectVisitor();
        statement.accept(mySqlSelectVisitor);

        String output = SQLUtils.toMySqlString(statement);
        Assert.assertNotNull(output);
    }


}
