package com.elong.pb.newdda.client.router.result.merge;

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
        if (resultSets.size() == 1) {
            return resultSets.get(0);
        }
        //多个结果集
        return null;
    }

}
