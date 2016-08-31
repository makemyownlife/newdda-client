
package com.elong.pb.newdda.client.router.result.router;


import java.util.ArrayList;
import java.util.List;

public final class Condition {
    
    private final Column column;
    
    private final BinaryOperator operator;

    public Condition(Condition.Column column, Condition.BinaryOperator operator) {
        this.column = column;
        this.operator = operator;
    }
    
    private final List<Comparable<?>> values = new ArrayList<Comparable<?>>();
    
    public static final class Column {
        
        private final String columnName;
        
        private final String tableName;

        public Column(String columnName, String tableName) {
            this.columnName = columnName;
            this.tableName = tableName;
        }

        public String getColumnName() {
            return this.columnName;
        }

        public String getTableName() {
            return this.tableName;
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
