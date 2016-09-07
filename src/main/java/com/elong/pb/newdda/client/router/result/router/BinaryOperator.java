package com.elong.pb.newdda.client.router.result.router;

/**
 * Created by zhangyong on 2016/9/7.
 */
public enum BinaryOperator {

    EQUAL("="), BETWEEN("BETWEEN"), IN("IN"), NOT_IN("NOT IN");

    private final String expression;

    private BinaryOperator(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }

}
