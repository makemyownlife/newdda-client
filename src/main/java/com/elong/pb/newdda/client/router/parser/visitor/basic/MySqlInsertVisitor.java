package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;

/**
 * MySQL的INSERT语句访问器.
 */
public class MySqlInsertVisitor extends AbstractMySqlVisitor{

    public MySqlInsertVisitor(Appendable appender) {
        super(appender);
    }

    public boolean visit(final MySqlInsertStatement x) {
        return true;
    }

}