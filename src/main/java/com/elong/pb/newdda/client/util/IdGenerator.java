package com.elong.pb.newdda.client.util;

/**
 * 1位 + 41bit(时间戳) + 10bit(机器id) + 12bit(序号) 最多一毫秒有4095个自增序列id
 * 模仿twitter的snowflake的做法
 * Created by zhangyong on 2016/10/13.
 */
public class IdGenerator {

    //总的分区数
    private final static int SHARDING_NUM = 1024;

    //最大机器id 1023 也就是 1111111111
    private final static int MAX_WORKER = SHARDING_NUM - 1;


}
