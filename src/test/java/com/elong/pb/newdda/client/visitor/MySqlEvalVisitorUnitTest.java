package com.elong.pb.newdda.client.visitor;

import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils;
import com.elong.pb.newdda.client.router.parser.visitor.basic.mysql.MySqlEvalVisitor;
import com.google.common.collect.Lists;
import org.testng.annotations.Test;

/**
 * Created by zhangyong on 2016/9/14.
 */
public class MySqlEvalVisitorUnitTest {

    @Test
    public void testVisit() throws Exception {
        SQLVariantRefExpr expr = new SQLVariantRefExpr("?");
        expr.setIndex(1);
        MySqlEvalVisitor visitor = new MySqlEvalVisitor();
        visitor.setParameters(Lists.<Object>newArrayList(1, 2));
        expr.accept(visitor);

        System.out.println((Integer) SQLEvalVisitorUtils.getValue(expr));
    }

}
