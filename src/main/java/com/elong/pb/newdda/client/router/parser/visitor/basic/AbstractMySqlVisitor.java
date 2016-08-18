package com.elong.pb.newdda.client.router.parser.visitor.basic;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.visitor.SqlParserContext;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMySqlVisitor extends MySqlOutputVisitor implements SqlVisitor {

    public static final String ATTR_DB           = "sharding.db";

    public static final String ATTR_PARTITION    = "sharding.partition";

    public static final String ATTR_TABLE_SOURCE = "sharding.tableSource";

    public static final String ATTR_ALIAS        = "sharding.alias";

    public static final String ATTR_TABLES       = "sharding.tables";

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
