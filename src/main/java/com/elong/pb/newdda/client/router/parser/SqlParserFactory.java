package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.db2.parser.DB2StatementParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.sqlserver.parser.SQLServerStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.exception.SQLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * sql 解析工厂类
 */
public final class SqlParserFactory {

    private final static Logger logger = LoggerFactory.getLogger(SqlParserFactory.class);

    public static SqlParserEngine create(final DatabaseType databaseType, final String sql, final List<Object> parameters, final Collection<String> shardingColumns) throws SQLParserException {
        if (logger.isDebugEnabled()) {
            logger.debug("Logic SQL: {}", sql);
        }
        SQLStatementParser sqlStatementParser = getSQLStatementParser(databaseType, sql);
        SQLStatement sqlStatement = sqlStatementParser.parseStatement();
        if (logger.isDebugEnabled()) {
            logger.trace("Get {} SQL Statement", sqlStatement.getClass().getName());
        }
        return null;
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


}
