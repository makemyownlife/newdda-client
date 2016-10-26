package com.elong.pb.newdda.client.router.rule;

import java.util.List;

/**
 * 数据源规则
 * Created by zhangyong on 2016/10/26.
 */
public interface DataSourceRule {

    List<String> getDataSourceList();

    String calculate(Long hash);

}
