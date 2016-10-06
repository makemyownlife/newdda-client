package com.elong.pb.newdda.client.router.action;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import com.elong.pb.newdda.client.router.result.router.*;
import com.elong.pb.newdda.client.router.rule.Algorithm;
import com.elong.pb.newdda.client.router.rule.AlgorithmResult;
import com.elong.pb.newdda.client.router.rule.ShardingRule;
import com.elong.pb.newdda.client.router.rule.TableRule;

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
        List<SqlExecutionUnit> result = new ArrayList<SqlExecutionUnit>();

        Collection<RouterCondition> conditions = conditionContext.getAllConditions();
        //判断该sql位于哪一个库,以及相关的表的替换
        for(RouterCondition condition : conditions){
            //判断该表 该列是否命中, 若命中, 则返回该表对应的数据源
            ShardingValue shardingValue = constructShardingValue(shardingRule , condition);
            if(shardingValue != null){
                AlgorithmResult algorithmResult = shardingRule.getAlgorithm().doAlgorithm(shardingValue);
                if(algorithmResult != null) {
                    SqlExecutionUnit sqlExecutionUnit = new SqlExecutionUnit(algorithmResult.getTargetDataSource(), sqlAppender);
                    result.add(sqlExecutionUnit);
                }
            }
        }
        return result;
    }

    //======================================================================== private method start ========================================================================

    private ShardingValue constructShardingValue(ShardingRule shardingRule , RouterCondition condition) {
        //所有的表规则(表以及列)
        List<TableRule> tableRuleList = shardingRule.getTableRules();
        //分区算法
        Algorithm algorithm = shardingRule.getAlgorithm();
        //分区算法涉及到的数据库
        List<String> dataSourceList = algorithm.getDataSourceList();

        RouterColumn routerColumn = condition.getRouterColumn();
        String tableName = routerColumn.getTableName();
        String columnName = routerColumn.getColumnName();

        //是否命中相关的表 以及分区关键字
        boolean isHit = false;
        for(TableRule tableRule : tableRuleList) {
            if(tableRule.getShardingKey().equals(columnName) && tableRule.getTableName().equals(tableName)) {
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
