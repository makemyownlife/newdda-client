package com.elong.pb.newdda.client.router.result.router;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by zhangyong on 2016/8/14.
 */
public class SqlAppender implements Appendable {

    private final static Logger logger = LoggerFactory.getLogger(SqlAppender.class);

    private final Collection<Object> segments = new LinkedList<Object>();

    private final Map<String, StringToken> tokenMap = new HashMap<String, StringToken>();

    private StringBuilder currentSegment;

    public SqlAppender() {
        this.currentSegment = new StringBuilder();
        this.segments.add(currentSegment);
    }

    /**
     * 增加占位符.
     *
     * @param token 占位符
     * @return SQL构建器
     */
    public SqlAppender appendToken(final String token) {
        return appendToken(token, true);
    }

    /**
     * 增加占位符.
     *
     * @param token 占位符
     * @param isSetValue 是否设置占位值
     * @return SQL构建器
     */
    public SqlAppender appendToken(final String token, final boolean isSetValue) {
        StringToken stringToken;
        if (tokenMap.containsKey(token)) {
            stringToken = tokenMap.get(token);
        } else {
            stringToken = new StringToken();
            if (isSetValue) {
                stringToken.value = token;
            }
            tokenMap.put(token, stringToken);
        }
        segments.add(stringToken);
        currentSegment = new StringBuilder();
        segments.add(currentSegment);
        return this;
    }

    /**
     * 用实际的值替代占位符.
     *
     * @param originToken 占位符
     * @param newToken 实际的值
     * @return SQL构建器
     */
    public SqlAppender buildSQL(final String originToken, final String newToken) {
        if (tokenMap.containsKey(originToken)) {
            tokenMap.get(originToken).value = newToken;
        }
        return this;
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
     * 复制构建器.
     *
     * @return 新的构建器
     */
    public SqlAppender cloneBuilder() {
        SqlAppender result = new SqlAppender();
        for (Object each : segments) {
            if (each instanceof StringToken) {
                StringToken token = (StringToken) each;
                StringToken newToken = new StringToken();
                newToken.value = token.value;
                result.segments.add(newToken);
                result.tokenMap.put(newToken.value, newToken);
            } else {
                result.segments.add(each);
            }
        }
        return result;
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
