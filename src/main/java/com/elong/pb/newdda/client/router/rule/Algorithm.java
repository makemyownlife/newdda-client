package com.elong.pb.newdda.client.router.rule;

import com.elong.pb.newdda.client.router.action.ShardingValue;

import java.util.List;

/**
 * 分区算法
 * Created by zhangyong on 2016/8/20.
 */
public interface Algorithm {

    List<String> getDataSourceList();

    AlgorithmResult doAlgorithm(ShardingValue shardingValue);

}
