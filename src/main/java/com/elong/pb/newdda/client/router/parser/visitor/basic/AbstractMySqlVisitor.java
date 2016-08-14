package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

public abstract class AbstractMySqlVisitor extends SQLASTOutputVisitor {

    protected AbstractMySqlVisitor() {
        super(new SqlBuilderForVisitor());
    }

}
