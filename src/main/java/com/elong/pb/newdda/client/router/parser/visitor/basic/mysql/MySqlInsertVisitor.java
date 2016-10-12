package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;

/**
 * MySQL的INSERT语句访问器.
 */
public class MySqlInsertVisitor extends AbstractMySqlVisitor {

    public boolean visit(final MySqlInsertStatement x) {
        getSqlParserContext().setCurrentTable(x.getTableName().toString(), x.getAlias());
        return super.visit(x);
    }

}
