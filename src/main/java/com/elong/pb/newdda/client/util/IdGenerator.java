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
    private final static int MAX_WORKER_ID = SHARDING_NUM - 1;

    //最大序号
    private final static int MAX_SEQ = 4095;

    private final static long workerIdBits = 10L;

    private final static long sequenceBits = 12L;

    private final static long workerIdShift = sequenceBits;

    private final static long timestampLeftShift = sequenceBits + workerIdBits;

    public static long getUniqueId(int workerId , int seqId) {
        if(workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("workerId is not Illegal");
        }
        if(seqId > MAX_SEQ || seqId < 0) {
            throw new IllegalArgumentException("seqId is not Illegal ");
        }
        //时间戳
        long timestamp = System.currentTimeMillis();
        //机器编号
        return  (timestamp << timestampLeftShift) | (workerId << workerIdShift) | seqId;
    }


}
