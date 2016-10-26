package com.elong.pb.newdda.client.router.action;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import com.elong.pb.newdda.client.router.result.router.*;
import com.elong.pb.newdda.client.router.rule.ShardingKey;
import com.elong.pb.newdda.client.router.rule.ShardingRule;

import java.util.*;

/**
 * 分区动作
 * Created by zhangyong on 2016/9/17.
 */
public class DefaultShardingAction implements ShardingAction {

    private ShardingRule shardingRule;

    private ConditionContext conditionContext;

    private Set<String> logicTables;

    private SqlAppender sqlAppender;

    private SqlStatementType sqlStatementType;

    public DefaultShardingAction(final ShardingRule shardingRule, final ConditionContext conditionContext, final Set<String> logicTables, final SqlAppender sqlAppender, final SqlStatementType sqlStatementType) {
        this.shardingRule = shardingRule;
        this.conditionContext = conditionContext;
        this.logicTables = logicTables;
        this.sqlAppender = sqlAppender;
        this.sqlStatementType = sqlStatementType;
    }

    @Override
    public Collection<SqlExecutionUnit> doSharding() {
        //分区的结果
        Set<SqlExecutionUnit> result = new HashSet<SqlExecutionUnit>();
        //组装分区值
        Collection<RouterCondition> conditions = conditionContext.getAllConditions();
        List<ShardingValue> shardingValues = new ArrayList<ShardingValue>(conditions.size());
        //判断该sql位于哪一个库,以及相关的表的替换
        for(RouterCondition condition : conditions) {
            //判断该表以及字段是否命中
            ShardingValue shardingValue = constructShardingValue(shardingRule, condition);
            if (shardingValue != null) {
                shardingValues.add(shardingValue);
            }
        }
        return result;
    }

    //======================================================================== private method start ========================================================================
    private ShardingValue constructShardingValue(ShardingRule shardingRule , RouterCondition condition) {
        //所有的表规则(表以及列)
        List<ShardingKey> shardingKeyList = shardingRule.getShardingKeyBeen();
        RouterColumn routerColumn = condition.getRouterColumn();
        String tableName = routerColumn.getTableName();
        String columnName = routerColumn.getColumnName();
        //是否命中相关的表 以及分区关键字
        boolean isHit = false;
        for(ShardingKey shardingKey : shardingKeyList) {
            if(shardingKey.getShardingColumn().equals(columnName) && shardingKey.getTableName().equals(tableName)) {
                isHit = true;
                break;
            }
        }
        if(!isHit) {
            return null;
        }
        List<Comparable<?>> conditionValues = condition.getValues();
        switch (condition.getOperator()) {
            case EQUAL:
            case IN:
                if (1 == conditionValues.size()) {
                    return new ShardingValue<Comparable<?>>(condition.getRouterColumn().getTableName(), condition.getRouterColumn().getColumnName(), conditionValues.get(0));
                }
              //TODO BETWEEN ? 暂时不处理 2016-09-30相关 第二版开发
            default:
                throw new UnsupportedOperationException(condition.getOperator().getExpression());
        }
    }
    //======================================================================== private method end ========================================================================


}
