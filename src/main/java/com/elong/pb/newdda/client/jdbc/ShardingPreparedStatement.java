package com.elong.pb.newdda.client.jdbc;

import com.elong.pb.newdda.client.executor.PreparedStatementExecutor;
import com.elong.pb.newdda.client.executor.wrapper.PreparedStatementExecutorWrapper;
import com.elong.pb.newdda.client.jdbc.adapter.AbstractPreparedStatementAdapter;
import com.elong.pb.newdda.client.router.PreparedSqlRouter;
import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import com.elong.pb.newdda.client.router.SqlRouterEngine;
import com.elong.pb.newdda.client.router.SqlRouterResult;
import com.elong.pb.newdda.client.router.result.merge.MergeContext;
import com.elong.pb.newdda.client.router.result.merge.ResultSetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyong on 2016/7/26.
 */
public class ShardingPreparedStatement extends AbstractPreparedStatementAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ShardingPreparedStatement.class);

    private PreparedSqlRouter preparedSqlRouter;

    private Integer autoGeneratedKeys;

    private int[] columnIndexes;

    private String[] columnNames;

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql) throws SQLException {
        this(shardingConnection, sqlRouterEngine, sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this(shardingConnection, sqlRouterEngine, sql, resultSetType, resultSetConcurrency, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        super(shardingConnection, sqlRouterEngine, resultSetType, resultSetConcurrency, resultSetHoldability);
        this.preparedSqlRouter = sqlRouterEngine.prepareSql(sql);
    }

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql, final int autoGeneratedKeys) throws SQLException {
        this(shardingConnection, sqlRouterEngine, sql);
        this.autoGeneratedKeys = autoGeneratedKeys;
    }

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql, final int[] columnIndexes) throws SQLException {
        this(shardingConnection, sqlRouterEngine, sql);
        this.columnIndexes = columnIndexes;
    }

    public ShardingPreparedStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final String sql, final String[] columnNames) throws SQLException {
        this(shardingConnection, sqlRouterEngine, sql);
        this.columnNames = columnNames;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ResultSet rs;
        try {
            MergeContext mergeContext = getMergeContext();
            List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers = routeSql();
            PreparedStatementExecutor preparedStatementExecutor = new PreparedStatementExecutor(preparedStatementExecutorWrappers);
            List<ResultSet> resultSetList = preparedStatementExecutor.executeQuery();
            rs = ResultSetFactory.getResultSet(resultSetList, mergeContext);
        } finally {
            clearRouteContext();
        }
        setCurrentResultSet(rs);
        return rs;
    }

    @Override
    public int executeUpdate() throws SQLException {
        try {
            List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers = routeSql();
            PreparedStatementExecutor preparedStatementExecutor = new PreparedStatementExecutor(preparedStatementExecutorWrappers);
            return preparedStatementExecutor.executeUpdate();
        } finally {
            clearRouteContext();
        }
    }

    @Override
    public boolean execute() throws SQLException {
        try {
            List<PreparedStatementExecutorWrapper> preparedStatementExecutorWrappers = routeSql();
            PreparedStatementExecutor preparedStatementExecutor = new PreparedStatementExecutor(preparedStatementExecutorWrappers);
            return preparedStatementExecutor.execute();
        } finally {
            clearRouteContext();
        }
    }

    @Override
    public void addBatch() throws SQLException {

    }

    private void clearRouteContext() throws SQLException {
        clearParameters();
        setCurrentResultSet(null);
    }

    private List<PreparedStatementExecutorWrapper> routeSql() throws SQLException {
        List<Object> parameters = getParameters();
        List<PreparedStatementExecutorWrapper> result = new ArrayList<PreparedStatementExecutorWrapper>();
        SqlRouterResult sqlRouteResult = preparedSqlRouter.route(parameters);
        MergeContext mergeContext = sqlRouteResult.getMergeContext();
        setMergeContext(mergeContext);
        for (SqlExecutionUnit each : sqlRouteResult.getExecutionUnits()) {
            PreparedStatement preparedStatement = (PreparedStatement) getStatement(getShardingConnection().getConnection(each.getDataSource(), sqlRouteResult.getSqlStatementType()), each.getSql());
            setParameters(preparedStatement, parameters);
            result.add(new PreparedStatementExecutorWrapper(preparedStatement, parameters, each));
        }
        return result;
    }

    protected PreparedStatement generateStatement(final Connection conn, final String shardingSql) throws SQLException {
        if (null != autoGeneratedKeys) {
            return conn.prepareStatement(shardingSql, autoGeneratedKeys);
        }
        if (null != columnIndexes) {
            return conn.prepareStatement(shardingSql, columnIndexes);
        }
        if (null != columnNames) {
            return conn.prepareStatement(shardingSql, columnNames);
        }
        if (0 != getResultSetHoldability()) {
            return conn.prepareStatement(shardingSql, getResultSetType(), getResultSetConcurrency(), getResultSetHoldability());
        }
        return conn.prepareStatement(shardingSql, getResultSetType(), getResultSetConcurrency());
    }

    private void setParameters(final PreparedStatement preparedStatement, final List<Object> parameters) throws SQLException {
        int i = 1;
        for (Object each : parameters) {
            preparedStatement.setObject(i++, each);
        }
    }

}
