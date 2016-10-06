package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.executor.wrapper.StatementExecutorWrapper;
import com.elong.pb.newdda.client.router.SqlExecutionUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StatementExecutor {

    private static final Logger logger = LoggerFactory.getLogger(StatementExecutor.class);

    //定义线程池
    private final List<StatementExecutorWrapper> statementExecutorWrappers = new ArrayList<StatementExecutorWrapper>();

    private final static int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private final static int MAX_SIZE = CORE_SIZE * 2  + 1;

    private final static int KEEP_ALIVE_TIME = 1800;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_SIZE,
            MAX_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 添加静态语句对象至执行上下文.
     *
     * @param statementExecutorWrapper 静态语句对象的执行上下文
     */
    public void addStatement(final StatementExecutorWrapper statementExecutorWrapper) {
        statementExecutorWrappers.add(statementExecutorWrapper);
    }

    public List<ResultSet> executeQuery() {
        List<ResultSet> result = null;
        return result;
    }

    private ResultSet executeQueryInternal(final StatementExecutorWrapper statementExecutorWrapper) {
        Statement statement = statementExecutorWrapper.getStatement();
        SqlExecutionUnit sqlExecutionUnit = statementExecutorWrapper.getSqlExecutionUnit();
        try {
            String sql = sqlExecutionUnit.getSql();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        }catch (SQLException ex) {
            logger.error("executeQueryInternal error: " ,ex);
            return null;
        }
    }

}
