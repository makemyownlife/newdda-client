package com.elong.pb.newdda.client.visitor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by zhangyong on 16/8/14.
 */
public class MySqlSchemaStatVisitorUnitTest {

    @Test
    public void test_0() throws Exception {
        String sql = "select a.name, b.name FROM users a, usergroups b on a.groupId = b.id";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);

        Assert.assertEquals(1, statementList.size());

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statemen.accept(visitor);

        System.out.println(sql);
        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());

        Assert.assertEquals(2, visitor.getTables().size());
        Assert.assertEquals(true, visitor.containsTable("users"));
        Assert.assertEquals(true, visitor.containsTable("usergroups"));

        Assert.assertEquals(4, visitor.getColumns().size());
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("users", "groupId")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("users", "name")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("usergroups", "id")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("usergroups", "name")));
    }

    public void test_1() throws Exception {
        String sql = "select a.name, b.name FROM users a, usergroups b on a.groupId = b.id where a.groupID = ?";

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statemen = statementList.get(0);

        Assert.assertEquals(1, statementList.size());

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statemen.accept(visitor);

        System.out.println(sql);
        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());

        Assert.assertEquals(2, visitor.getTables().size());
        Assert.assertEquals(true, visitor.containsTable("users"));
        Assert.assertEquals(true, visitor.containsTable("usergroups"));

        Assert.assertEquals(4, visitor.getColumns().size());
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("users", "groupId")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("users", "name")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("usergroups", "id")));
        Assert.assertEquals(true, visitor.getColumns().contains(new TableStat.Column("usergroups", "name")));
    }


}
