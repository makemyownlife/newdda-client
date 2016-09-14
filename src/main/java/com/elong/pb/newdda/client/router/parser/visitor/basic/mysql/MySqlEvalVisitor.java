package com.elong.pb.newdda.client.router.parser.visitor.basic.mysql;

import com.alibaba.druid.sql.ast.expr.SQLVariantRefExpr;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlEvalVisitorImpl;

import java.util.Map;

/**
 * MySQL变量中提取参数值与编号.
 */
public class MySqlEvalVisitor extends MySqlEvalVisitorImpl {

    public static final String EVAL_VAR_INDEX = "EVAL_VAR_INDEX";

    @Override
    public boolean visit(final SQLVariantRefExpr x) {
        if (!"?".equals(x.getName())) {
            return false;
        }

        Map<String, Object> attributes = x.getAttributes();

        int varIndex = x.getIndex();

        if (varIndex == -1 || getParameters().size() <= varIndex) {
            return false;
        }
        if (attributes.containsKey(EVAL_VALUE)) {
            return false;
        }
        Object value = getParameters().get(varIndex);
        if (value == null) {
            value = EVAL_VALUE_NULL;
        }
        attributes.put(EVAL_VALUE, value);
        attributes.put(EVAL_VAR_INDEX, varIndex);
        return false;
    }

}
