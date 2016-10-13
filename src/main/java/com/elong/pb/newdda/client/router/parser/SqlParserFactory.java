package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.sqlserver.parser.SQLServerStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.exception.SqlParserException;
import com.elong.pb.newdda.client.router.parser.visitor.VisitorLogProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * sql 解析工厂类
 */
public final class SqlParserFactory {

    private final static Logger logger = LoggerFactory.getLogger(SqlParserFactory.class);

    public static SqlParserEngine createParserEngine(final DatabaseType databaseType, final String sql, final List<Object> parameters) throws SqlParserException {
        if (logger.isDebugEnabled()) {
            logger.debug("Logic SQL: {}", sql);
        }
        SQLStatementParser sqlStatementParser = getSQLStatementParser(databaseType, sql);
        SQLStatement sqlStatement = sqlStatementParser.parseStatement();
        if (logger.isDebugEnabled()) {
            logger.debug("Get {} SQL Statement", sqlStatement.getClass().getName());
        }
        SQLASTOutputVisitor sqlastOutputVisitor = getSQLVisitor(databaseType, sqlStatement);
        SqlParserEngine sqlParserEngine = new SqlParserEngine(sqlStatement, parameters, sqlastOutputVisitor);
        return sqlParserEngine;
    }

    private static SQLStatementParser getSQLStatementParser(final DatabaseType databaseType, final String sql) {
        switch (databaseType) {
            case H2:
            case MySQL:
                return new MySqlStatementParser(sql);
            case Oracle:
                return new OracleStatementParser(sql);
            case SQLServer:
                return new SQLServerStatementParser(sql);
            case DB2:
                return new DB2StatementParser(sql);
            default:
                throw new UnsupportedOperationException(String.format("Cannot support database type [%s]", databaseType));
        }
    }

    private static SQLASTOutputVisitor getSQLVisitor(final DatabaseType databaseType, final SQLStatement sqlStatement) {
        if (sqlStatement instanceof SQLSelectStatement) {
            return VisitorLogProxy.enhance(SqlVisitorRegistry.getSelectVistor(databaseType));
        }
        if (sqlStatement instanceof SQLInsertStatement) {
            return VisitorLogProxy.enhance(SqlVisitorRegistry.getInsertVistor(databaseType));
        }
        if (sqlStatement instanceof SQLUpdateStatement) {
            return VisitorLogProxy.enhance(SqlVisitorRegistry.getUpdateVistor(databaseType));
        }
        if (sqlStatement instanceof SQLDeleteStatement) {
            return VisitorLogProxy.enhance(SqlVisitorRegistry.getDeleteVistor(databaseType));
        }
        throw new SqlParserException("Unsupported SQL statement: [%s]", sqlStatement);
    }

}
