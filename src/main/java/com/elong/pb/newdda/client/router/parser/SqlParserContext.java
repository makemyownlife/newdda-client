package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.visitor.SQLEvalVisitor;
import com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils;
import com.alibaba.druid.util.JdbcUtils;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlEvalVisitor;
import com.elong.pb.newdda.client.router.result.router.BinaryOperator;
import com.elong.pb.newdda.client.router.result.router.ConditionContext;
import com.elong.pb.newdda.client.router.result.router.RouterColumn;
import com.elong.pb.newdda.client.router.result.router.RouterTable;
import com.elong.pb.newdda.client.util.SqlUtil;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析的上下文
 * Created by zhangyong on 2016/8/18.
 */
public class SqlParserContext {

    private final static Logger logger = LoggerFactory.getLogger(SqlParserContext.class);

    private RouterTable currentRouterTable;

    private List<Object> shardingColumns;

    private boolean hasOrCondition = false;

    private final ConditionContext currentConditionContext = new ConditionContext();

    private final SqlParserResult sqlParserResult = new SqlParserResult();

    public void setCurrentTable(final String currentTableName, final String currentAlias) {
        String exactlyAlias = SqlUtil.getExactlyValue(currentAlias);
        String exactlyTableName = SqlUtil.getExactlyValue(currentTableName);
        RouterTable routerTable = new RouterTable(exactlyTableName, exactlyAlias);
        sqlParserResult.getRouteContext().getRouterTables().add(routerTable);
        this.currentRouterTable = routerTable;
    }

    public void addCondition(final SQLExpr expr, final BinaryOperator operator, final List<SQLExpr> valueExprList, final DatabaseType databaseType, final List<Object> paramters) {
        RouterColumn routerColumn = getRouterColumn(expr);
        if (routerColumn == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("routerColumn:{}", routerColumn);
        }
        List<ValuePair> values = new ArrayList<ValuePair>(valueExprList.size());
        for (SQLExpr each : valueExprList) {
           ValuePair evalValue = evalExpression(databaseType, each, paramters);
            if (null != evalValue) {
                values.add(evalValue);
            }
        }
        if (values.isEmpty()) {
            return;
        }
        addCondition(routerColumn,operator,values);
    }

    public RouterTable addTable(final SQLExprTableSource x) {
        String tableName = SqlUtil.getExactlyValue(x.getExpr().toString());
        String alias = SqlUtil.getExactlyValue(x.getAlias());
        RouterTable result = new RouterTable(tableName, alias);
        sqlParserResult.getRouteContext().getRouterTables().add(result);
        return result;
    }

    /**
     * 判断SQL表达式是否为二元操作且带有别名.
     *
     * @param x                待判断的SQL表达式
     * @param tableOrAliasName 表名称或别名
     * @return 是否为二元操作且带有别名
     */
    public boolean isBinaryOperateWithAlias(final SQLPropertyExpr x, final String tableOrAliasName) {
        RouterTable routerTable = findTableFromAlias(SqlUtil.getExactlyValue(tableOrAliasName));
        if (routerTable == null) {
            return false;
        }
        return (x.getParent() instanceof SQLBinaryOpExpr);
    }

    /**
     * 将当前解析的条件对象归并入解析结果
     */
    public void mergeCurrentConditionContext() {
        if (!sqlParserResult.getRouteContext().getRouterTables().isEmpty()) {
            if (sqlParserResult.getConditionContexts().isEmpty()) {
                sqlParserResult.getConditionContexts().add(currentConditionContext);
            }
            return;
        }
        //是否需要解析子解析上下文 ? 不太能理解

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
        String aliasName = ((SQLIdentifierExpr) expr.getOwner()).getName();
        RouterTable routerTable = findTable(aliasName);
        if (routerTable == null) {
            return null;
        }
        RouterColumn routerColumn = createColumn(expr.getName(), routerTable.getName());
        return routerColumn;
    }

    private RouterColumn getColumnWithoutAlias(final SQLIdentifierExpr expr) {
        return (null != currentRouterTable) ? createColumn(expr.getName(), currentRouterTable.getName()) : null;
    }

    private RouterTable findTableFromName(final String name) {
        for (RouterTable each : sqlParserResult.getRouteContext().getRouterTables()) {
            if (each.getName().equalsIgnoreCase(SqlUtil.getExactlyValue(name))) {
                return each;
            }
        }
        return null;
    }

    private RouterTable findTableFromAlias(final String alias) {
        for (RouterTable each : sqlParserResult.getRouteContext().getRouterTables()) {
            if (each.getAlias() != null && each.getAlias().equalsIgnoreCase(SqlUtil.getExactlyValue(alias))) {
                return each;
            }
        }
        return null;
    }

    private RouterTable findTable(final String tableNameOrAlias) {
        RouterTable routerTable = findTableFromName(tableNameOrAlias);
        return routerTable == null ?
                findTableFromAlias(tableNameOrAlias) :
                routerTable;
    }

    private RouterColumn createColumn(final String columnName, final String tableName) {
        return new RouterColumn(
                SqlUtil.getExactlyValue(columnName),
                SqlUtil.getExactlyValue(tableName)
        );
    }

    private ValuePair evalExpression(final DatabaseType databaseType, final SQLObject sqlObject, final List<Object> parameters) {
        if (sqlObject instanceof SQLMethodInvokeExpr) {
            // TODO 解析函数中的sharingValue不支持
            return null;
        }
        SQLEvalVisitor visitor;
        if(JdbcUtils.MYSQL.equals(databaseType.name().toLowerCase()) || JdbcUtils.H2.equals(databaseType.name().toLowerCase())) {
            visitor = new MySqlEvalVisitor();
        } else {
            visitor = SQLEvalVisitorUtils.createEvalVisitor(databaseType.name());
        }
        visitor.setParameters(parameters);
        sqlObject.accept(visitor);
        Object value = SQLEvalVisitorUtils.getValue(sqlObject);
        if (null == value) {
            // TODO 对于NULL目前解析为空字符串,此处待考虑解决方法
            return null;
        }
        Comparable<?> finalValue;
        if (value instanceof Comparable<?>) {
            finalValue = (Comparable<?>) value;
        } else {
            finalValue = "";
        }
        Integer index = (Integer) sqlObject.getAttribute(MySqlEvalVisitor.EVAL_VAR_INDEX);
        if (null == index) {
            index = -1;
        }
        return new ValuePair(finalValue, index);
    }

    private void addCondition(final RouterColumn routerColumn, final BinaryOperator operator, final List<ValuePair> valuePairs) {


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

    //============================================================set get method end ==========================================================

    private static class ValuePair {

        private final Comparable<?> value;

        private final Integer paramIndex;

        public ValuePair(Comparable<?> value, Integer paramIndex) {
            this.value = value;
            this.paramIndex = paramIndex;
        }

        public Comparable<?> getValue() {
            return value;
        }

        public Integer getParamIndex() {
            return paramIndex;
        }

    }

}
