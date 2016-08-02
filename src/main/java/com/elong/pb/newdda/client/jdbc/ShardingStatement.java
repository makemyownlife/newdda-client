package com.elong.pb.newdda.client.jdbc;

import com.dangdang.ddframe.rdb.sharding.executor.StatementExecutor;
import com.elong.pb.newdda.client.jdbc.adapter.AbstractStatementAdapter;
import com.elong.pb.newdda.client.router.SqlRouterEngine;
import com.google.common.hash.HashCode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明：Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 * <p>
 * 通用格式为：Statement stmt=con.createStatement(int type，int concurrency);我们在访问数据库的时候，在读取返回结果的时候，可能要前后移动指针，比如我们先计算有多少条信息，这是我们就需要把指针移到最后来计算，然后再把指针移到最前面，逐条读取，有时我们只需要逐条读取就可以了。还有就是有只我们只需要读取数据，为了不破坏数据，我们可采用只读模式，有时我们需要望数据库里添加记录，这是我们就要采用可更新数据库的模式。下面我们就对其参数进行说明：
 * <p>
 * 参数 int type
 * <p>
 * ResultSet.TYPE_FORWORD_ONLY 结果集的游标只能向下滚动。
 * <p>
 * ResultSet.TYPE_SCROLL_INSENSITIVE 结果集的游标可以上下移动，当数据库变化时，当前结果集不变。
 * <p>
 * ResultSet.TYPE_SCROLL_SENSITIVE 返回可滚动的结果集，当数据库变化时，当前结果集同步改变。
 * <p>
 * 参数 int concurrency
 * <p>
 * ResultSet.CONCUR_READ_ONLY 不能用结果集更新数据库中的表。
 * <p>
 * ResultSet.CONCUR_UPDATETABLE 能用结果集更新数据库中的表。
 * <p>
 * 查询语句
 * <p>
 * ResultSet re=stmt.executeUpdate(SQL语句）；用来更新数据库信息或插入数据
 * <p>
 * ResultSet re=stmt.executeQuery(SQL语句）；用来查询数据库信息
 * <p>
 * 当我们使用ResultSet re=stmt.executeQuery(SQL语句）查询后，我们可以使用下列方法获得信息：
 * <p>
 * public boolean previous() 将游标向上移动，该方法返回boolean型数据，当移到结果集第一行之前时，返回false。
 * <p>
 * public void beforeFirst 将游标移动到结果集的初始位置，即在第一行之前。
 * <p>
 * public void afterLast() 将游标移到结果集最后一行之后。
 * <p>
 * public void first() 将游标移到结果集的第一行。
 * <p>
 * public void last() 将游标移到结果集的最后一行。
 * <p>
 * public boolean isAfterLast() 判断游标是否在最后一行之后。
 * <p>
 * public boolean isBeforeFirst() 判断游标是否在第一行之前。
 * <p>
 * public boolean ifFirst() 判断游标是否指向结果集的第一行。
 * <p>
 * public boolean isLast() 判断游标是否指向结果集的最后一行。
 * <p>
 * public int getRow() 得到当前游标所指向行的行号，行号从1开始，如果结果集没有行，返回0。
 * <p>
 * public boolean absolute(int row) 将游标移到参数row指定的行号。如果row取负值，就是倒数的行数，absolute(-1)表示移到最后一行，absolute(-2)表示移到倒数第2行。当移动到第一行前面或最后一行的后面时，该方法返回false
 * 说明：Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
 * <p>
 * 通用格式为：Statement stmt=con.createStatement(int type，int concurrency);我们在访问数据库的时候，在读取返回结果的时候，可能要前后移动指针，比如我们先计算有多少条信息，这是我们就需要把指针移到最后来计算，然后再把指针移到最前面，逐条读取，有时我们只需要逐条读取就可以了。还有就是有只我们只需要读取数据，为了不破坏数据，我们可采用只读模式，有时我们需要望数据库里添加记录，这是我们就要采用可更新数据库的模式。下面我们就对其参数进行说明：
 * <p>
 * 参数 int type
 * <p>
 * ResultSet.TYPE_FORWORD_ONLY 结果集的游标只能向下滚动。
 * <p>
 * ResultSet.TYPE_SCROLL_INSENSITIVE 结果集的游标可以上下移动，当数据库变化时，当前结果集不变。
 * <p>
 * ResultSet.TYPE_SCROLL_SENSITIVE 返回可滚动的结果集，当数据库变化时，当前结果集同步改变。
 * <p>
 * 参数 int concurrency
 * <p>
 * ResultSet.CONCUR_READ_ONLY 不能用结果集更新数据库中的表。
 * <p>
 * ResultSet.CONCUR_UPDATETABLE 能用结果集更新数据库中的表。
 * <p>
 * 查询语句
 * <p>
 * ResultSet re=stmt.executeUpdate(SQL语句）；用来更新数据库信息或插入数据
 * <p>
 * ResultSet re=stmt.executeQuery(SQL语句）；用来查询数据库信息
 * <p>
 * 当我们使用ResultSet re=stmt.executeQuery(SQL语句）查询后，我们可以使用下列方法获得信息：
 * <p>
 * public boolean previous() 将游标向上移动，该方法返回boolean型数据，当移到结果集第一行之前时，返回false。
 * <p>
 * public void beforeFirst 将游标移动到结果集的初始位置，即在第一行之前。
 * <p>
 * public void afterLast() 将游标移到结果集最后一行之后。
 * <p>
 * public void first() 将游标移到结果集的第一行。
 * <p>
 * public void last() 将游标移到结果集的最后一行。
 * <p>
 * public boolean isAfterLast() 判断游标是否在最后一行之后。
 * <p>
 * public boolean isBeforeFirst() 判断游标是否在第一行之前。
 * <p>
 * public boolean ifFirst() 判断游标是否指向结果集的第一行。
 * <p>
 * public boolean isLast() 判断游标是否指向结果集的最后一行。
 * <p>
 * public int getRow() 得到当前游标所指向行的行号，行号从1开始，如果结果集没有行，返回0。
 * public boolean absolute(int row) 将游标移到参数row指定的行号。如果row取负值，就是倒数的行数，absolute(-1)表示移到最后一行，absolute(-2)表示移到倒数第2行。当移动到第一行前面或最后一行的后面时，该方法返回false
 * Created by zhangyong on 2016/7/26.
 **/
public class ShardingStatement extends AbstractStatementAdapter {

    //====================================================================================================================================
    private final Map<HashCode, Statement> cachedRoutedStatements = new HashMap<HashCode, Statement>();

    private ResultSet currentResultSet;

    //====================================================== 初始化参数  ================================================================
    private final ShardingConnection shardingConnection;

    private final SqlRouterEngine sqlRouterEngine;

    private final int resultSetType;

    private final int resultSetConcurrency;

    private final int resultSetHoldability;

    //====================================================== 初始化参数 ================================================================

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
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }


    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
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

    //======================================================================= 基本参数 end ==================================================================================

    @Override
    public ResultSet getResultSet() throws SQLException {
        return currentResultSet;
    }

    //======================================================================= private method start ==================================================================

    public StatementExecutor generateExecutor(final String sql) throws SQLException {
        return null;
    }

    //======================================================================= private method end ==================================================================

}
