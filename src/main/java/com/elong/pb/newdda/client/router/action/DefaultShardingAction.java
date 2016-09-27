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
        return null;
    }

}
