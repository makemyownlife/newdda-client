package com.elong.pb.newdda.exception;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/31
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class ShardingJdbcException extends RuntimeException {

    private static final long serialVersionUID = -1343739516839252250L;

    public ShardingJdbcException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public ShardingJdbcException(final Exception cause) {
        super(cause);
    }

}
