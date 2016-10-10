package com.elong.pb.newdda.client.parser;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlSelectParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySql2OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLSelectParser;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 16/8/6.
 */
public class SqlParserUnitTest {

    @Test
    public void testSelect() throws Exception {
        String sql = "SELECT COUNT(*)  FROM  close_plan WHERE 1=1          AND close_type = ?             AND target_type = ?             AND target_id = ?         AND(    mi_name=?   )               AND end_time >= ?         ";
        SQLSelectParser parser = new MySqlSelectParser(sql);
        SQLSelect select = parser.select();

        StringBuilder out = new StringBuilder();
        MySql2OracleOutputVisitor visitor = new MySql2OracleOutputVisitor(out);

        select.accept(visitor);
    }

    @Test
    public void testPrepareSelect() throws Exception {

        String sql = "   SELECT * FROM close_plan WHERE 1=1 AND close_type = ? AND target_type = ? ";
        SQLSelectParser parser = new MySqlSelectParser(sql);
        SQLSelect select = parser.select();

        StringBuilder out = new StringBuilder();
        MySql2OracleOutputVisitor visitor = new MySql2OracleOutputVisitor(out);

        List<Object> paramList = new ArrayList<Object>();
        paramList.add(3);
        paramList.add(12);
        visitor.setParameters(paramList);

        select.accept(visitor);

        System.out.println(out);
    }

}
