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
        //ast parser是将输入文本转换为ast（抽象语法树），parser有包括两个部分，Parser和Lexer，其中Lexer实现词法分析，Parser实现语法分析。
        //AST是Abstract Syntax Tree的缩写，也就是抽象语法树。AST是parser输出的结果。Visitor是遍历AST的手段，是处理AST最方便的模式，Visitor是一个接口，有缺省什么都没做的实现VistorAdapter
        visitor.setParameters(parameters);
        sqlStatement.accept(visitor);

        return null;
    }

}
