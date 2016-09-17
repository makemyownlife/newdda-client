package com.elong.pb.newdda.client.router.action;

import com.elong.pb.newdda.client.router.SqlExecutionUnit;

import java.util.Collection;

/**
 * 分区动作
 * Created by zhangyong on 2016/9/17.
 */
public interface ShardingAction {

    Collection<SqlExecutionUnit> doSharding();

}
