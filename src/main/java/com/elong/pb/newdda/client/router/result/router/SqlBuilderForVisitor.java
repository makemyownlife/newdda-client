package com.elong.pb.newdda.client.router.result.router;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by zhangyong on 2016/8/14.
 */
public class SqlBuilderForVisitor implements Appendable {

    private final static Logger logger = LoggerFactory.getLogger(SqlBuilderForVisitor.class);

    private final Collection<Object> segments = new LinkedList<Object>();

    private StringBuilder currentSegment;

    public SqlBuilderForVisitor() {
        this.currentSegment = new StringBuilder();
        this.segments.add(currentSegment);
    }

    @Override
    public Appendable append(CharSequence sql) throws IOException {
        currentSegment.append(sql);
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Appendable append(char c) throws IOException {
        currentSegment.append(c);
        return this;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Object each : segments) {
            if (each instanceof StringToken) {
                result.append(((StringToken) each).toToken());
            } else {
                result.append(each.toString());
            }
        }
        return result.toString();
    }

    /**
     * 生成SQL语句.
     */
    public String toSQL() {
        StringBuilder result = new StringBuilder();
        for (Object each : segments) {
            result.append(each.toString());
        }
        return result.toString();
    }

    private class StringToken {

        private String value;

        public String toToken() {
            return null == value ? "" : Joiner.on("").join("[Token(", value, ")]");
        }

        @Override
        public String toString() {
            return null == value ? "" : value;
        }
    }

}
