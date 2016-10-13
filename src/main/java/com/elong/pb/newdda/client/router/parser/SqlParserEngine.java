package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.elong.pb.newdda.client.exception.SqlParserException;
import com.elong.pb.newdda.client.router.parser.visitor.SqlVisitor;
import com.elong.pb.newdda.client.router.result.router.SqlStatementType;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by zhangyong on 2016/8/2.
 */
public class SqlParserEngine {

    private SQLStatement sqlStatement;

    private List<Object> parameters;

    private SQLASTOutputVisitor visitor;

    public SqlParserEngine(SQLStatement sqlStatement, List<Object> parameters, SQLASTOutputVisitor visitor) {
        this.sqlStatement = sqlStatement;
        this.parameters = parameters;
        this.visitor = visitor;
    }

    public SqlParserResult parse() {
        //判断验证参数
        Preconditions.checkArgument(visitor instanceof SqlVisitor);

        SqlVisitor sqlVisitor = (SqlVisitor) visitor;
        SqlParserContext sqlParserContext = sqlVisitor.getSqlParserContext();
        //ast parser是将输入文本转换为ast（抽象语法树），parser有包括两个部分，Parser和Lexer，其中Lexer实现词法分析，Parser实现语法分析。
        //AST是Abstract Syntax Tree的缩写，也就是抽象语法树。AST是parser输出的结果。Visitor是遍历AST的手段，是处理AST最方便的模式，Visitor是一个接口，有缺省什么都没做的实现VistorAdapter
        visitor.setParameters(parameters);
        sqlStatement.accept(visitor);

        //将当前解析的条件对象归并入解析结果
        sqlParserContext.mergeCurrentConditionContext();

        //急躁 、易怒是我情绪上的弱点 ,加油 ~~~~~
        SqlParserResult sqlParserResult = sqlParserContext.getSqlParsedResult();
        sqlParserResult.getRouteContext().setSqlAppender(sqlVisitor.getSqlAppender());
        sqlParserResult.getRouteContext().setSqlStatementType(getType());

        return sqlParserResult;
    }

    private SqlStatementType getType() {
        if (sqlStatement instanceof SQLSelectStatement) {
            return SqlStatementType.SELECT;
        }
        if (sqlStatement instanceof SQLInsertStatement) {
            return SqlStatementType.INSERT;
        }
        if (sqlStatement instanceof SQLUpdateStatement) {
            return SqlStatementType.UPDATE;
        }
        if (sqlStatement instanceof SQLDeleteStatement) {
            return SqlStatementType.DELETE;
        }
        throw new SqlParserException("Unsupported SQL statement: [%s]", sqlStatement);
    }

}
