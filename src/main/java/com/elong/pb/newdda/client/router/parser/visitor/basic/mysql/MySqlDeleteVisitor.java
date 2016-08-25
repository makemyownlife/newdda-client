package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;

public class MySqlDeleteVisitor extends AbstractMySqlVisitor {

    public boolean visit(final MySqlDeleteStatement x) {
        return true;
    }

}
