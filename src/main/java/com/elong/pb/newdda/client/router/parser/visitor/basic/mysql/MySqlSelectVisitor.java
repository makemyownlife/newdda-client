package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MySQL的SELECT语句访问器.
 */
public class MySqlSelectVisitor extends AbstractMySqlVisitor {

    private final static Logger logger = LoggerFactory.getLogger(MySqlSelectVisitor.class);

    public void preVisit(SQLObject x) {
        if (logger.isDebugEnabled()) {
        }
    }

    @Override
    public boolean visit(final MySqlSelectQueryBlock x) {
        if (x.getFrom() instanceof SQLExprTableSource) {
            SQLExprTableSource tableExpr = (SQLExprTableSource) x.getFrom();
            getSqlParserContext().setCurrentTable(tableExpr.getExpr().toString(), tableExpr.getAlias());
        }
        return super.visit(x);
    }

    @Override
    public void endVisit(final MySqlSelectQueryBlock x) {
        super.endVisit(x);
    }

}
