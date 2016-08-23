package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLLiteralExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.visitor.SqlParserContext;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;

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
            identifierExpr = (SQLIdentifierExpr)((SQLPropertyExpr) x).getOwner();
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
