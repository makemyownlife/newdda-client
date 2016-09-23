package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.ast.SQLHint;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.SqlParserContext;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;
import com.elong.pb.newdda.client.router.result.router.BinaryOperator;
import com.elong.pb.newdda.client.router.result.router.RouterTable;
import com.elong.pb.newdda.client.router.result.router.SqlAppender;
import com.elong.pb.newdda.client.util.SqlUtil;

import java.util.Arrays;

public abstract class AbstractMySqlVisitor extends MySqlOutputVisitor implements SqlVisitor {

    private SqlParserContext sqlParserContext;

    protected AbstractMySqlVisitor() {
        super(new SqlAppender());
        super.setPrettyFormat(false);
        this.sqlParserContext = new SqlParserContext();
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        RouterTable routerTable = sqlParserContext.addTable(x);
        printToken(routerTable.getName());
        if (routerTable.getAlias() != null) {
            print(' ');
            print(routerTable.getAlias());
        }
        for (SQLHint each : x.getHints()) {
            print(' ');
            each.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SQLBinaryOpExpr x) {
        //既然我活了下来就不能白白活着 -- 梅长苏
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

    public final boolean visit(final SQLPropertyExpr x) {
        if (!(x.getParent() instanceof SQLBinaryOpExpr) && !(x.getParent() instanceof SQLSelectItem)) {
            return super.visit(x);
        }
        if (!(x.getOwner() instanceof SQLIdentifierExpr)) {
            return super.visit(x);
        }
        String tableOrAliasName = ((SQLIdentifierExpr) x.getOwner()).getLowerName();
        if (getSqlParserContext().isBinaryOperateWithAlias(x, tableOrAliasName)) {
            return super.visit(x);
        }
        printToken(tableOrAliasName);
        print(".");
        print(x.getName());
        return false;
    }

    @Override
    public boolean visit(final SQLBetweenExpr x) {
        sqlParserContext.addCondition(x.getTestExpr(), BinaryOperator.BETWEEN, Arrays.asList(x.getBeginExpr(), x.getEndExpr()), getDatabaseType(), getParameters());
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
    public SqlAppender getSqlAppender() {
        return (SqlAppender) appender;
    }

    @Override
    public final void printToken(final String token) {
        getSqlAppender().appendToken(SqlUtil.getExactlyValue(token));
    }

    //===================================================================get method end========================================================================

}
