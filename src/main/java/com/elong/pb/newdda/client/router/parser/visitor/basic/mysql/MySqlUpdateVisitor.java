package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;

/**
 * MySQL的UPDATE语句访问器.
 */
public class MySqlUpdateVisitor extends AbstractMySqlVisitor {

    public boolean visit(final MySqlUpdateStatement x) {
        return true;
    }

}