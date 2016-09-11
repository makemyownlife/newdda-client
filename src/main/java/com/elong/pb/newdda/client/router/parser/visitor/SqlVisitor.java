package com.elong.pb.newdda.client.router.parser.visitor;

import com.elong.pb.newdda.client.constants.DatabaseType;
import com.elong.pb.newdda.client.router.parser.SqlParserContext;
import com.elong.pb.newdda.client.router.result.router.SqlBuilderForVisitor;

/**
 * Created by zhangyong on 2016/8/18.
 */
public interface SqlVisitor {

    /**
     * 获取数据库类型.
     *
     * @return 数据库类型
     */
    DatabaseType getDatabaseType();

    /**
     * 获取解析上下文对象.
     *
     * @return 解析上下文对象
     */
    SqlParserContext getSqlParserContext();

    /**
     * 获取SQL构建器.
     * SQL构建器
     */
    SqlBuilderForVisitor getSqlBuilder();

    /**
     * 打印替换标记.
     *
     * @param token 替换标记
     */
    void printToken(String token);

}
