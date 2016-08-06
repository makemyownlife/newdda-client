package com.elong.pb.newdda.client.exception;


/**
 * Created by zhangyong on 2016/7/26.
 */
public class SqlParserException extends ShardingJdbcException {

    private static final long serialVersionUID = -1498980479829506655L;

    public SqlParserException(final String message, final Object... args) {
        super(String.format(message, args));
    }

}
