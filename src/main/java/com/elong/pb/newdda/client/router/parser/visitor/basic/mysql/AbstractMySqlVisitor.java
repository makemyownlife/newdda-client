package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.SqlParserContext;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;
import com.elong.pb.newdda.client.router.result.router.BinaryOperator;
import com.elong.pb.newdda.client.router.result.router.SqlBuilderForVisitor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMySqlVisitor extends MySqlOutputVisitor implements SqlVisitor {

    private SqlParserContext sqlParserContext;

    protected AbstractMySqlVisitor() {
        super(new SqlBuilderForVisitor());
        super.setPrettyFormat(false);
        this.sqlParserContext = new SqlParserContext();
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        sqlParserContext.addTable(x);
        return false;
    }

    @Override
    public boolean visit(SQLIdentifierExpr x) {
        return false;
    }

    @Override
    public boolean visit(SQLBinaryOpExpr x) {
        //既然我活了下来就不能白白活着 -- 梅长苏
        x.getLeft().setParent(x);
        x.getRight().setParent(x);

        x.getLeft().accept(this);
        x.getRight().accept(this);

        switch (x.getOperator()) {
            case BooleanOr:
                sqlParserContext.setHasOrCondition(true);
                break;
            case Equality:
                sqlParserContext.addCondition(x.getLeft(), BinaryOperator.EQUAL, Arrays.asList(x.getRight()), getDatabaseType(), getParameters());
                sqlParserContext.addCondition(x.getRight(), BinaryOperator.EQUAL, Arrays.asList(x.getLeft()), getDatabaseType(), getParameters());
                break;
            default:
                break;
        }

        return super.visit(x);
    }

    //===================================================================get method  start========================================================================
    @Override
    public final DatabaseType getDatabaseType() {
        return DatabaseType.MySQL;
    }

    @Override
    public SqlParserContext getSqlParserContext() {
        return sqlParserContext;
    }

    /**
     * 获取SQL构建器.
     * SQL构建器
     */
    @Override
    public SqlBuilderForVisitor getSqlBuilder() {
        return (SqlBuilderForVisitor) appender;
    }

    //===================================================================get method end========================================================================

    @Override
    public final void printToken(final String token) {

    }

}
