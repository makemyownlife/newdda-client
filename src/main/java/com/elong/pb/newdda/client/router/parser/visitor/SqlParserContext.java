package com.elong.pb.newdda.client.router.parser.visitor;

import com.elong.pb.newdda.client.router.parser.SqlParserResult;
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

    private final SqlParserResult sqlParserResult = new SqlParserResult();

    //============================================================set get method start ===================================================

    public void setShardingColumns(List<Object> shardingColumns) {
        this.shardingColumns = shardingColumns;
    }

    public SqlParserResult getSqlParsedResult() {
        return this.sqlParserResult;
    }

    //============================================================set get method end ===================================================

}
