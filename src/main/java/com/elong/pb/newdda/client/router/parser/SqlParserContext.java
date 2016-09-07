package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.result.router.BinaryOperator;
import com.elong.pb.newdda.client.router.result.router.RouterColumn;
import com.elong.pb.newdda.client.router.result.router.RouterTable;
import com.elong.pb.newdda.client.util.SQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 解析的上下文
 * Created by zhangyong on 2016/8/18.
 */
public class SqlParserContext {

    private final static Logger logger = LoggerFactory.getLogger(SqlParserContext.class);

    private List<Object> shardingColumns;

    private boolean hasOrCondition = false;

    private final SqlParserResult sqlParserResult = new SqlParserResult();

    public void addCondition(final SQLExpr expr, final BinaryOperator operator, final List<SQLExpr> valueExprs, final DatabaseType databaseType, final List<Object> paramters) {
        RouterColumn routerColumn = getRouterColumn(expr);
    }

    public RouterTable addTable(final SQLExprTableSource x) {
        String tableName = SQLUtil.getExactlyValue(x.getExpr().toString());
        String alias = SQLUtil.getExactlyValue(x.getAlias());
        RouterTable result = new RouterTable(tableName, alias);
        sqlParserResult.getRouteContext().getTables().add(result);
        return result;
    }

    //=========================================================== private method start ==========================================================

    private RouterColumn getRouterColumn(final SQLExpr expr) {
        if (expr instanceof SQLPropertyExpr) {
            return getColumnWithQualifiedName((SQLPropertyExpr) expr);
        }
        if (expr instanceof SQLIdentifierExpr) {
            return getColumnWithoutAlias((SQLIdentifierExpr) expr);
        }
        return null;
    }

    private RouterColumn getColumnWithQualifiedName(final SQLPropertyExpr expr) {

        return null;
    }

    private RouterColumn getColumnWithoutAlias(final SQLIdentifierExpr expr) {
        return null;
    }

    private RouterTable findTable(final String tableNameOrAlias) {
        return null;
    }

    //=========================================================== private method end =======================================================


    //============================================================set get method start =====================================================

    public void setShardingColumns(List<Object> shardingColumns) {
        this.shardingColumns = shardingColumns;
    }

    public SqlParserResult getSqlParsedResult() {
        return this.sqlParserResult;
    }

    public boolean isHasOrCondition() {
        return hasOrCondition;
    }

    public void setHasOrCondition(boolean hasOrCondition) {
        this.hasOrCondition = hasOrCondition;
    }

    //============================================================set get method end ========================================================

}
