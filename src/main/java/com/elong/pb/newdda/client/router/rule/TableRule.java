package com.elong.pb.newdda.client.router.rule;

/**
 * Created by zhangyong on 2016/10/26.
 */
public interface TableRule {

    String calculate(String logicTableName, Long hash);

}
