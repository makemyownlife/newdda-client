package com.elong.pb.newdda.client.router.result.merge.resultset;

import com.elong.pb.newdda.client.router.result.merge.ResultSetMergeContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class IteratorReducerResultSet extends AbstractDelegateResultSet {
    
    private int resultSetIndex = 1;
    
    public IteratorReducerResultSet(final ResultSetMergeContext resultSetMergeContext) throws SQLException {
        super(resultSetMergeContext.getShardingResultSets().getResultSets());
    }
    
    @Override
    protected boolean firstNext() throws SQLException {
        return processCurrent();
    }
    
    @Override
    protected boolean afterFirstNext() throws SQLException {
        return processCurrent() || (!isOutOfIndex() && processNext());
    }
    
    private boolean processCurrent() throws SQLException {
        return getDelegate().next();
    }
    
    private boolean isOutOfIndex() {
        return resultSetIndex >= getResultSets().size();
    }
    
    private boolean processNext() throws SQLException {
        ResultSet resultSet = getResultSets().get(resultSetIndex++);
        setDelegate(resultSet);
        return resultSet.next();
    }

}
