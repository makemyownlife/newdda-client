package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhangyong on 2016/8/2.
 */
public class SqlParserEngine {

    private SQLStatement sqlStatement;

    private List<Object> parameters;

    private SQLASTOutputVisitor visitor;

    private List<String> shardingColumns;

    public SqlParserEngine(SQLStatement sqlStatement, List<Object> parameters, SQLASTOutputVisitor visitor, final List<String> shardingColumns) {
        this.sqlStatement = sqlStatement;
        this.parameters = parameters;
        this.visitor = visitor;
        this.shardingColumns = shardingColumns;
    }

    public SqlParserResult parse() {

        return null;
    }


}
