package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * MySQL的SELECT语句访问器.
 */
public class MySqlSelectVisitor extends AbstractMySqlVisitor {

    private final static Logger logger = LoggerFactory.getLogger(MySqlSelectVisitor.class);

    public void preVisit(SQLObject x) {
        if (logger.isDebugEnabled()) {
            System.out.println(x.getClass().getSimpleName() + ":" + x);
        }
    }

    @Override
    public boolean visit(final MySqlSelectQueryBlock x) {
        if (x.getFrom() != null) {
            x.getFrom().setParent(x);
            x.getFrom().accept(this);
        }
        if (x.getWhere() != null) {
            x.getWhere().setParent(x);
            x.getWhere().accept(this);
        }
        return false;
    }

    //============================================================================   重写相关的visit astnode  start================================================================
    @Override
    public boolean visit(SQLJoinTableSource x) {
        x.getLeft().setParent(x);
        x.getRight().setParent(x);
        if (x.getCondition() != null) {
            x.getCondition().setParent(x);
        }
        return true;
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        Map<String, SQLTableSource> aliasMap = getAliasMap(x);
        if (aliasMap != null) {
            if (x.getAlias() != null) {
                aliasMap.put(x.getAlias(), x);
            }
            if (x.getExpr() instanceof SQLIdentifierExpr) {
                String tableName = ((SQLIdentifierExpr) x.getExpr()).getName();
                aliasMap.put(tableName, x);
            }
        }
        return false;
    }

    //便宜表名 并且加入到到本地缓存中
    @Override
    public boolean visit(SQLIdentifierExpr x) {
        SQLTableSource tableSource = getTableSource(x.getName(), x.getParent());
        if (tableSource != null) {
            x.putAttribute(ATTR_TABLE_SOURCE, tableSource);
        }
        return false;
    }

    @Override
    public boolean visit(SQLBinaryOpExpr x) {
        //既然我活了下来就不能白白活着 -- 梅长苏
        x.getLeft().setParent(x);
        x.getRight().setParent(x);

        x.getLeft().accept(this);
        x.getRight().accept(this);

        // x:a.id = b.order_id AND a.id IN (1, 2, 3, 4)  则 column 为null
        String column = getColumn(x.getLeft());
        boolean isValue = isValue(x.getRight());
        if (column != null && isValue) {
            SQLTableSource tableSource = getBinaryOpExprLeftOrRightTableSource(x.getLeft());
            System.out.println(tableSource);
        }

        return false;
    }

    //============================================================================   重写相关的visit astnode  end================================================================

}
