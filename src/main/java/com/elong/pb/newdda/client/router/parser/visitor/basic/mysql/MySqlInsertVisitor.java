package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.elong.pb.newdda.client.router.result.router.BinaryOperator;

/**
 * MySQL的INSERT语句访问器.
 */
public class MySqlInsertVisitor extends AbstractMySqlVisitor {

    public boolean visit(final MySqlInsertStatement x) {
        getSqlParserContext().setCurrentTable(x.getTableName().toString(), x.getAlias());
        if (null == x.getValues()) {
            return super.visit(x);
        }
        for (int i = 0; i < x.getColumns().size(); i++) {
            getSqlParserContext().addCondition(x.getColumns().get(i).toString(), x.getTableName().toString(), BinaryOperator.EQUAL, x.getValues().getValues().get(i), getDatabaseType(), getParameters());
        }
        return super.visit(x);
    }

}
