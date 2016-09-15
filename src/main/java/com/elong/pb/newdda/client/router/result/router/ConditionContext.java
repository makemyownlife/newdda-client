package com.elong.pb.newdda.client.router.result.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyong on 16/9/15.
 */
public class ConditionContext {

    private final Map<RouterColumn, RouterCondition> conditions = new LinkedHashMap<RouterColumn, RouterCondition>();

    /**
     * 添加条件对象.
     *
     * @param condition 条件对象
     */
    public void add(final RouterCondition condition) {
        // TODO 自关联有问题，表名可考虑使用别名对应
        conditions.put(condition.getRouterColumn(), condition);
    }

    /**
     * 查找条件对象.
     *
     * @param table 表名称
     * @param column 列名称
     * @return 条件对象
     */
    public RouterCondition find(final String table, final String column) {
        return conditions.get(new RouterColumn(column, table));
    }

    /**
     * 查找条件对象.
     *
     * @param table 表名称
     * @param column 列名称
     * @param operator 操作符
     * @return 条件对象
     */
    public RouterCondition find(final String table, final String column, final BinaryOperator operator) {
        RouterCondition result = find(table, column);
        if(result == null) {
            return result;
        }
        return result.getOperator() == operator ? result : null;
    }

    public boolean isEmpty() {
        return conditions.isEmpty();
    }

    public void clear() {
        conditions.clear();
    }

    public Collection<RouterCondition> getAllConditions() {
        return conditions.values();
    }

    public void setNewConditionValue(final List<Object> parameters) {
        for (RouterCondition each : conditions.values()) {
            if (each.getValueIndices().isEmpty()) {
                continue;
            }
            for (int i = 0; i < each.getValueIndices().size(); i++) {
                Object value = parameters.get(each.getValueIndices().get(i));
                if (value instanceof Comparable<?>) {
                    each.getValues().set(i, (Comparable<?>) value);
                } else {
                    each.getValues().set(i, "");
                }
            }
        }
    }

}
