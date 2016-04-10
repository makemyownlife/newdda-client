package com.elong.pb.newdda.client.jdbc.adapter;


import com.elong.pb.newdda.client.jdbc.unsupported.AbstractUnsupportedOperationConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/31
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConnectionAdapter extends AbstractUnsupportedOperationConnection {

    private boolean autoCommit = true;

    private boolean readOnly = true;

    private boolean closed;

    private int transactionIsolation = TRANSACTION_READ_UNCOMMITTED;

    protected abstract Collection<Connection> getConnections();

    @Override
    public final boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public final void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.autoCommit = autoCommit;
        if (getConnections().isEmpty()) {
            return;
        }
        for (Connection each : getConnections()) {
            each.setAutoCommit(autoCommit);
        }
    }

    @Override
    public final void commit() throws SQLException {
        for (Connection each : getConnections()) {
            each.commit();
        }
    }

    @Override
    public final void rollback() throws SQLException {
        for (Connection each : getConnections()) {
            each.rollback();
        }
    }

    @Override
    public final void close() throws SQLException {
        for (Connection each : getConnections()) {
            each.close();
        }
        closed = true;
    }

    @Override
    public final boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public final boolean isReadOnly() throws SQLException {
        return readOnly;
    }

    @Override
    public final void setReadOnly(final boolean readOnly) throws SQLException {
        this.readOnly = readOnly;
        if (getConnections().isEmpty()) {
            return;
        }
        for (Connection each : getConnections()) {
            each.setReadOnly(readOnly);
        }
    }

    @Override
    public final int getTransactionIsolation() throws SQLException {
        return transactionIsolation;
    }

    @Override
    public final void setTransactionIsolation(final int level) throws SQLException {
        transactionIsolation = level;
        if (getConnections().isEmpty()) {
            return;
        }
        for (Connection each : getConnections()) {
            each.setTransactionIsolation(level);
        }
    }

    //==============================================以下代码与MySQL实现保持一致 ================================
    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public final int getHoldability() throws SQLException {
        return ResultSet.CLOSE_CURSORS_AT_COMMIT;
    }

    @Override
    public final void setHoldability(final int holdability) throws SQLException {

    }

}
