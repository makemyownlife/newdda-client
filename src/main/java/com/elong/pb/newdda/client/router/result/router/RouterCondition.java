package com.elong.pb.newdda.client.router.result.router;

import java.util.ArrayList;
import java.util.List;

public final class RouterCondition {

    private final RouterColumn routerColumn;

    private final BinaryOperator operator;

    private final List<Comparable<?>> values = new ArrayList<Comparable<?>>();

    private final List<Integer> valueIndices = new ArrayList<Integer>();

    public RouterCondition(RouterColumn column, BinaryOperator operator) {
        this.routerColumn = column;
        this.operator = operator;
    }

    public RouterColumn getRouterColumn() {
        return routerColumn;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public List<Comparable<?>> getValues() {
        return values;
    }

    public List<Integer> getValueIndices() {
        return valueIndices;
    }

}
