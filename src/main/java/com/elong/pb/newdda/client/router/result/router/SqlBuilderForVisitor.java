package com.elong.pb.newdda.client.router.result.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by zhangyong on 2016/8/14.
 */
public class SqlBuilderForVisitor implements Appendable {

    private final static Logger logger = LoggerFactory.getLogger(SqlBuilderForVisitor.class);

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        return null;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Appendable append(char c) throws IOException {
        return null;
    }

}
