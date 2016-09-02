
package com.elong.pb.newdda.client.router.result.router;


import java.util.ArrayList;
import java.util.List;

public final class RouterCondition {
    
    private final RouterColumn routerColumn;
    
    private final BinaryOperator operator;

    public RouterCondition(RouterCondition.RouterColumn column, RouterCondition.BinaryOperator operator) {
        this.routerColumn = column;
        this.operator = operator;
    }
    
    private final List<Comparable<?>> values = new ArrayList<Comparable<?>>();
    
    public static final class RouterColumn {
        
        private final String columnName;
        
        private final String tableName;

        public RouterColumn(String columnName, String tableName) {
            this.columnName = columnName;
            this.tableName = tableName;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getTableName() {
            return tableName;
        }
    }
    
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


}
