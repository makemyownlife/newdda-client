package com.elong.pb.newdda.client.router.result.router;

import java.util.ArrayList;
import java.util.List;

public final class RouterCondition {

    private final RouterColumn routerColumn;

    private final BinaryOperator operator;

    private final List<Comparable<?>> values = new ArrayList<Comparable<?>>();

    public RouterCondition(RouterColumn column, BinaryOperator operator) {
        this.routerColumn = column;
        this.operator = operator;
    }

}
