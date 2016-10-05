package com.elong.pb.newdda.client.executor;

import com.elong.pb.newdda.client.executor.wrapper.StatementExecutorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatementExecutor {

    private static final Logger logger = LoggerFactory.getLogger(StatementExecutor.class);

    private final Collection<StatementExecutorWrapper> statementExecutorWrappers = new ArrayList<StatementExecutorWrapper>();

    /**
     * 添加静态语句对象至执行上下文.
     *
     * @param statementExecutorWrapper 静态语句对象的执行上下文
     */
    public void addStatement(final StatementExecutorWrapper statementExecutorWrapper) {
        statementExecutorWrappers.add(statementExecutorWrapper);
    }

    public List<ResultSet> executeQuery() {
        return null;
    }

}
