package com.elong.pb.newdda.client.jdbc;

import com.elong.pb.newdda.client.router.result.merge.resultset.WrapperResultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2016/10/9.
 */
public class ShardingResultSets {

    private final List<ResultSet> resultSets;

    private ResultSetOperateType resultSetOperateType;

    public ShardingResultSets(final List<ResultSet> resultSets) throws SQLException {
        this.resultSets = filterResultSets(resultSets);
        this.resultSetOperateType = generateType();
    }

    private List<ResultSet> filterResultSets(final List<ResultSet> resultSets) throws SQLException {
        List<ResultSet> result = new ArrayList<ResultSet>(resultSets.size());
        for (ResultSet each : resultSets) {
            if (each.next()) {
                result.add(new WrapperResultSet(each));
            }
        }
        return result;
    }

    private ResultSetOperateType generateType() {
        if (this.resultSets.isEmpty()) {
            return ResultSetOperateType.EMPTY;
        } else if (1 == this.resultSets.size()) {
            return ResultSetOperateType.SINGLE;
        } else {
            return ResultSetOperateType.MULTIPLE;
        }
    }

    enum ResultSetOperateType {
        EMPTY, SINGLE, MULTIPLE
    }

    //===============================================================get set method start =========================================================================

    public List<ResultSet> getResultSets() {
        return resultSets;
    }

    public ResultSetOperateType getResultSetOperateType() {
        return resultSetOperateType;
    }

    //===============================================================get set method end  =========================================================================

}
