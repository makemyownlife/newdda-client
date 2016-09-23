package com.elong.pb.newdda.client.router.action;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import com.elong.pb.newdda.client.router.result.router.*;
import com.elong.pb.newdda.client.router.rule.Algorithm;
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
        Collection<SqlExecutionUnit> result = new ArrayList<SqlExecutionUnit>();

        //所有的表规则(表以及列)
        List<TableRule> tableRuleList = shardingRule.getTableRules();
        //分区算法
        Algorithm algorithm = shardingRule.getAlgorithm();
        //分区算法涉及到的数据库
        List<String> dataSourceList = algorithm.getDataSourceList();

        //计算位于哪一个分区
        Collection<RouterCondition> conditions = conditionContext.getAllConditions();
        for(RouterCondition condition : conditions) {
            //暂时先计算 = 不考虑其他的条件符
            if(condition.getOperator() == BinaryOperator.EQUAL) {
                boolean isHit = hitTableColumn(condition , tableRuleList);
                if(isHit) {
                }
            }
        }

        return result;
    }

    //判断是否命中相关的表
    private boolean hitTableColumn(RouterCondition condition ,List<TableRule>  tableRuleList) {
        RouterColumn routerColumn = condition.getRouterColumn();
        String tableName = routerColumn.getTableName();
        String columnName = routerColumn.getColumnName();
        boolean isHit = false;
        for(TableRule tableRule : tableRuleList) {
            if(tableRule.getShardingKey().equals(columnName) && tableRule.getTableName().equals(tableName)) {
                isHit = true;
                break;
            }
        }
        return isHit;
    }

}
