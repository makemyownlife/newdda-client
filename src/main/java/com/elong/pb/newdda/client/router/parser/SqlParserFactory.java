package com.elong.pb.newdda.client.router.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
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
        SQLStatement sqlStatement = null;
        if (logger.isDebugEnabled()) {
            logger.trace("Get {} SQL Statement", sqlStatement.getClass().getName());
        }
        return null;
    }


}
