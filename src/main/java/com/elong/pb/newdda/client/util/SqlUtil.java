package com.elong.pb.newdda.client.util;

import com.google.common.base.CharMatcher;

/**
 * SQL工具类.
 */
public class SqlUtil {

    /**
     * 去掉SQL表达式的特殊字符.
     *
     * @param value SQL表达式
     * @return 去掉SQL特殊字符的表达式
     */
    public static String getExactlyValue(final String value) {
        return null == value ? null : CharMatcher.anyOf("[]`'\"").removeFrom(value);
    }

}
