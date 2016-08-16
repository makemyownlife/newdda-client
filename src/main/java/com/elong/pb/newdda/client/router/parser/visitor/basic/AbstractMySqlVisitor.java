package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

public abstract class AbstractMySqlVisitor extends MySqlOutputVisitor {

    protected AbstractMySqlVisitor() {
        super(new SqlBuilderForVisitor());
        super.setPrettyFormat(false);
    }

}
