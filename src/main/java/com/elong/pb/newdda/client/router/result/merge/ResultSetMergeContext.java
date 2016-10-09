package com.elong.pb.newdda.client.router.result.merge;

import com.elong.pb.newdda.client.jdbc.ShardingResultSets;

import java.sql.ResultSet;

/**
 * Created by zhangyong on 2016/10/9.
 */
public class ResultSetMergeContext {

    private ShardingResultSets shardingResultSets;

    private MergeContext mergeContext;

    public ResultSetMergeContext(ShardingResultSets shardingResultSets, MergeContext mergeContext) {
        this.shardingResultSets = shardingResultSets;
        this.mergeContext = mergeContext;
    }

    //=========================================================================set get method start =========================================================================

    public ShardingResultSets getShardingResultSets() {
        return shardingResultSets;
    }

    //=========================================================================set get method end =========================================================================


}
