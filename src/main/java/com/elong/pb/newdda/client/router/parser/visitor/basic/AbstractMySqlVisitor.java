package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.elong.pb.newdda.client.router.parser.visitor.SqlParserContext;

public abstract class AbstractMySqlVisitor extends MySqlOutputVisitor {

    private SqlParserContext sqlParserContext;

    protected AbstractMySqlVisitor() {
        super(new SqlBuilderForVisitor());
        super.setPrettyFormat(false);
        this.sqlParserContext = new SqlParserContext();
    }

}
