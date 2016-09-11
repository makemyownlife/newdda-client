package com.elong.pb.newdda.client.router.parser.visitor;

import com.elong.pb.newdda.client.exception.ShardingJdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VisitorLogProxy {

    private final static Logger logger = LoggerFactory.getLogger(VisitorLogProxy.class);

    public static <T> T enhance(final Class<T> target) {
        try {
            return target.newInstance();
        } catch (InstantiationException ex) {
            logger.error("create Visitor exception: {}", ex);
            throw new ShardingJdbcException(ex);
        } catch (IllegalAccessException e) {
            logger.error("create Visitor exception: {}", e);
            throw new ShardingJdbcException(e);
        }
    }

}
