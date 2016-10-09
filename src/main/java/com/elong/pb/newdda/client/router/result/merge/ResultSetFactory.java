package com.elong.pb.newdda.client.router.result.merge;

import com.elong.pb.newdda.client.jdbc.ShardingResultSets;
import com.elong.pb.newdda.client.router.result.merge.resultset.IteratorReducerResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhangyong on 2016/9/16.
 */
public class ResultSetFactory {

    private final static Logger logger = LoggerFactory.getLogger(ResultSetFactory.class);

    public static ResultSet getResultSet(final List<ResultSet> resultSets, final MergeContext mergeContext) throws SQLException {
        //无结果集
        if (resultSets == null) {
            return null;
        }
        //单个结果集
        else if (resultSets.size() == 1) {
            return resultSets.get(0);
        }
        //多个结果集
        else {
            ShardingResultSets shardingResultSets = new ShardingResultSets(resultSets);
            ResultSetMergeContext resultSetMergeContext = new ResultSetMergeContext(shardingResultSets, mergeContext);
            return createMultipleResultSet(resultSetMergeContext);
        }
    }

    private static ResultSet createMultipleResultSet(ResultSetMergeContext resultSetMergeContext) throws SQLException {
        return new IteratorReducerResultSet(resultSetMergeContext);
    }

}
