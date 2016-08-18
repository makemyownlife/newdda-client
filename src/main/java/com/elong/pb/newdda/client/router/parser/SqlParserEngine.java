package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.elong.pb.newdda.client.router.parser.visitor.SqlParserContext;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by zhangyong on 2016/8/2.
 */
public class SqlParserEngine {

    private SQLStatement sqlStatement;

    private List<Object> parameters;

    private SQLASTOutputVisitor visitor;

    private List<Object> shardingColumns;

    public SqlParserEngine(SQLStatement sqlStatement, List<Object> parameters, SQLASTOutputVisitor visitor, final List<Object> shardingColumns) {
        this.sqlStatement = sqlStatement;
        this.parameters = parameters;
        this.visitor = visitor;
        this.shardingColumns = shardingColumns;
    }

    public SqlParserResult parse() {
        //判断验证参数
        Preconditions.checkArgument(visitor instanceof SqlVisitor);
        SqlVisitor sqlVisitor = (SqlVisitor) visitor;
        SqlParserContext sqlParserContext = sqlVisitor.getSqlParserContext();
        sqlParserContext.setShardingColumns(shardingColumns);
        visitor.setParameters(parameters);
        sqlStatement.accept(visitor);

        return null;
    }

}
