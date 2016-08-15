package com.elong.pb.newdda.client.visitor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.elong.pb.newdda.client.router.parser.visitor.basic.MySqlSelectVisitor;
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


    }

}
