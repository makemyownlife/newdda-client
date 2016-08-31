package com.elong.pb.newdda.client.router.parser.visitor;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.SqlParserResult;
import com.elong.pb.newdda.client.router.result.router.Condition;
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


    public void addCondition(final SQLExpr expr, final Condition.BinaryOperator operator, final List<SQLExpr> valueExprs, final DatabaseType databaseType, final List<Object> paramters) {

    }

    //============================================================set get method start ===================================================

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

    //============================================================set get method end ===================================================

}
