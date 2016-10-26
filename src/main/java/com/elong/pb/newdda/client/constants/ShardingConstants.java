package com.elong.pb.newdda.client.constants;

/**
 * 分区常量
 * Created by zhangyong on 2016/10/17.
 */
public class ShardingConstants {

    //系统默认最大分区数
    public static final int SHARDING_LENGTH = 1024;

    //表分区列表
    private static final int PARTITION_LENGTH = ShardingConstants.SHARDING_LENGTH;

    // %转换为&操作的换算数值
    public static final long AND_VALUE = PARTITION_LENGTH - 1;

}
