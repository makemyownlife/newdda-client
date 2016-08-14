package com.elong.pb.newdda.client.parser;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlSelectParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySql2OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLSelectParser;
import org.testng.annotations.Test;

/**
 * Created by zhangyong on 16/8/6.
 */
public class SqlParserTest{

    @Test
    public void testSelect() throws Exception {
        String sql = "   SELECT COUNT(*) FROM close_plan WHERE 1=1          AND close_type = ?             AND target_type = ?             AND target_id = ?         AND(    mi_name=?   )               AND end_time >= ?         ";
        SQLSelectParser parser = new MySqlSelectParser(sql);
        SQLSelect select = parser.select();

        StringBuilder out = new StringBuilder();
        MySql2OracleOutputVisitor visitor = new MySql2OracleOutputVisitor(out);

        select.accept(visitor);

        System.out.println(out);
    }

}