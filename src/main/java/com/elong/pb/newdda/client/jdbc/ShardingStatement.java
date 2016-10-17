package com.elong.pb.newdda.client.jdbc;

import com.elong.pb.newdda.client.executor.StatementExecutor;
import com.elong.pb.newdda.client.executor.wrapper.StatementExecutorWrapper;
import com.elong.pb.newdda.client.jdbc.adapter.AbstractStatementAdapter;
import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import com.elong.pb.newdda.client.router.SqlRouterEngine;
import com.elong.pb.newdda.client.router.SqlRouterResult;
import com.elong.pb.newdda.client.router.result.merge.MergeContext;
import com.elong.pb.newdda.client.router.result.merge.ResultSetFactory;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ShardingStatement extends AbstractStatementAdapter {

    //=================================================================================  当前的结果  ===========================================================================================
    private final Map<HashCode, Statement> cachedRoutedStatements = new HashMap<HashCode, Statement>();

    private ResultSet currentResultSet = null;

    private MergeContext mergeContext;

    //===================================================================================================== 初始化参数 start ================================================================
    private final ShardingConnection shardingConnection;

    private final SqlRouterEngine sqlRouterEngine;

    private final int resultSetType;

    private final int resultSetConcurrency;

    private final int resultSetHoldability;

    //======================================================================================================= 初始化参数 end================================================================

    public ShardingStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine) throws SQLException {
        this(shardingConnection, sqlRouterEngine, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public ShardingStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this(shardingConnection, sqlRouterEngine, resultSetType, resultSetConcurrency, ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }

    public ShardingStatement(final ShardingConnection shardingConnection, final SqlRouterEngine sqlRouterEngine, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) {
        this.shardingConnection = shardingConnection;
        this.sqlRouterEngine = sqlRouterEngine;
        this.resultSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.resultSetHoldability = resultSetHoldability;
    }

    public Collection<? extends Statement> getRoutedStatements() throws SQLException {
        return cachedRoutedStatements.values();
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        //若存在结果集,则关闭当前的结果集
        if (null != currentResultSet && !currentResultSet.isClosed()) {
            currentResultSet.close();
        }

        StatementExecutor statementExecutor = generateExecutor(sql);
        List<ResultSet> resultSetsList = statementExecutor.executeQuery();
        currentResultSet = ResultSetFactory.getResultSet(resultSetsList, mergeContext);
        return currentResultSet;
    }

    @Override
    public int executeUpdate(final String sql) throws SQLException {
        return generateExecutor(sql).executeUpdate();
    }

    @Override
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        return generateExecutor(sql).executeUpdate(autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        return generateExecutor(sql).executeUpdate(columnIndexes);
    }

    @Override
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        return generateExecutor(sql).executeUpdate(columnNames);
    }

    @Override
    public boolean execute(final String sql) throws SQLException {
        return generateExecutor(sql).execute();
    }

    @Override
    public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
        return generateExecutor(sql).execute(autoGeneratedKeys);
    }

    @Override
    public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
        return generateExecutor(sql).execute(columnIndexes);
    }

    @Override
    public boolean execute(final String sql, final String[] columnNames) throws SQLException {
        return generateExecutor(sql).execute(columnNames);
    }

    //======================================================================= 基本参数 start ==================================================================================
    @Override
    public int getResultSetConcurrency() throws SQLException {
        return resultSetConcurrency;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return resultSetType;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return shardingConnection;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return resultSetHoldability;
    }

    public ResultSet getCurrentResultSet() {
        return currentResultSet;
    }

    public void setCurrentResultSet(ResultSet currentResultSet) {
        this.currentResultSet = currentResultSet;
    }

    public MergeContext getMergeContext() {
        return mergeContext;
    }

    public ShardingConnection getShardingConnection() {
        return shardingConnection;
    }

    public void setMergeContext(MergeContext mergeContext) {
        this.mergeContext = mergeContext;
    }

    //======================================================================= 基本参数 end ==================================================================================
    @Override
    public ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }
    //======================================================================= private method start ==================================================================

    public StatementExecutor generateExecutor(final String sql) throws SQLException {
        StatementExecutor statementExecutor = new StatementExecutor();

        SqlRouterResult sqlRouteResult = sqlRouterEngine.route(sql, Collections.emptyList());
        this.mergeContext = sqlRouteResult.getMergeContext();

        for (SqlExecutionUnit each : sqlRouteResult.getExecutionUnits()) {
            String dataSource = each.getDataSource();
            Connection connection = shardingConnection.getConnection(
                    dataSource,
                    sqlRouteResult.getSqlStatementType());
            Statement statement = getStatement(connection, sql);
            statementExecutor.addStatement(new StatementExecutorWrapper(statement, each));
        }

        return statementExecutor;
    }

    protected Statement getStatement(final Connection connection, final String sql) throws SQLException {
        HashCode hashCode = Hashing.md5().newHasher().putInt(connection.hashCode()).putString(sql, Charsets.UTF_8).hash();
        if (cachedRoutedStatements.containsKey(hashCode)) {
            return cachedRoutedStatements.get(hashCode);
        }
        Statement statement = generateStatement(connection, sql);
        cachedRoutedStatements.put(hashCode, statement);
        return statement;
    }

    protected Statement generateStatement(final Connection connection, final String sql) throws SQLException {
        Statement result;
        if (0 == resultSetHoldability) {
            result = connection.createStatement(resultSetType, resultSetConcurrency);
        } else {
            result = connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        return result;
    }

    //======================================================================= private method end ==================================================================
}
