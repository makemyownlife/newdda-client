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

    public static final String ATTR_DB = "sharding.db";

    public static final String ATTR_PARTITION = "sharding.partition";

    public static final String ATTR_TABLE_SOURCE = "sharding.tableSource";

    public static final String ATTR_ALIAS = "sharding.alias";

    public static final String ATTR_TABLES = "sharding.tables";

    private SqlParserContext sqlParserContext;

    protected AbstractMySqlVisitor() {
        super(new SqlBuilderForVisitor());
        super.setPrettyFormat(false);
        this.sqlParserContext = new SqlParserContext();
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

        sqlParserContext.addTable(x);
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

    public static SQLTableSource getTableSource(String name, SQLObject parent) {
        Map<String, SQLTableSource> aliasMap = getAliasMap(parent);
        if (aliasMap == null) {
            return null;
        }
        SQLTableSource tableSource = aliasMap.get(name);
        if (tableSource != null) {
            return tableSource;
        }
        for (Map.Entry<String, SQLTableSource> entry : aliasMap.entrySet()) {
            if (name.equalsIgnoreCase(entry.getKey())) {
                return tableSource;
            }
        }


        return null;
    }

    public static Map<String, SQLTableSource> getAliasMap(SQLObject x) {
        if (x == null) {
            return null;
        }
        if (x instanceof SQLSelectQueryBlock || x instanceof SQLDeleteStatement) {
            Map<String, SQLTableSource> map = (Map<String, SQLTableSource>) x.getAttribute(ATTR_ALIAS);
            if (map == null) {
                map = new HashMap<String, SQLTableSource>();
                x.putAttribute(ATTR_ALIAS, map);
            }
            return map;
        }
        return getAliasMap(x.getParent());
    }

    public static SQLTableSource getDefaultTableSource(SQLObject x) {
        if (x == null) {
            return null;
        }
        if (x instanceof SQLSelectQueryBlock) {
            return ((SQLSelectQueryBlock) x).getFrom();
        }
        if (x instanceof SQLDeleteStatement) {
            return ((SQLDeleteStatement) x).getTableSource();
        }
        if (x instanceof SQLUpdateStatement) {
            return ((SQLUpdateStatement) x).getTableSource();
        }
        return getDefaultTableSource(x.getParent());
    }

    public static boolean isValue(SQLExpr x) {
        return x instanceof SQLLiteralExpr || x instanceof SQLVariantRefExpr;
    }

    public static String getColumn(SQLExpr x) {
        if (x instanceof SQLPropertyExpr) {
            return ((SQLPropertyExpr) x).getName();
        }
        if (x instanceof SQLIdentifierExpr) {
            return ((SQLIdentifierExpr) x).getName();
        }
        return null;
    }

    public static SQLTableSource getBinaryOpExprLeftOrRightTableSource(SQLExpr x) {
        SQLIdentifierExpr identifierExpr = null;
        if (x instanceof SQLPropertyExpr) {
            identifierExpr = (SQLIdentifierExpr) ((SQLPropertyExpr) x).getOwner();
        }
        if (x instanceof SQLIdentifierExpr) {
            identifierExpr = (SQLIdentifierExpr) x;
        }
        SQLTableSource tableSource = (SQLTableSource) identifierExpr.getAttribute(ATTR_TABLE_SOURCE);
        if (tableSource != null) {
            return tableSource;
        }
        SQLTableSource defaultTableSource = getDefaultTableSource(x.getParent());
        if (defaultTableSource instanceof SQLExprTableSource) {
            SQLExpr expr = ((SQLExprTableSource) defaultTableSource).getExpr();
            if (expr instanceof SQLIdentifierExpr) {
                x.putAttribute(ATTR_TABLE_SOURCE, defaultTableSource);
                return defaultTableSource;
            }
        }
        return null;
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

    //===================================================================get method end========================================================================

    @Override
    public final void printToken(final String token) {
    }

}
