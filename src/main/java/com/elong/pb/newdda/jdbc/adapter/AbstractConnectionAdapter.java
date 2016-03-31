package com.elong.pb.newdda.jdbc.adapter;

import com.elong.pb.newdda.jdbc.unsupported.AbstractUnsupportedOperationConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/31
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConnectionAdapter extends AbstractUnsupportedOperationConnection {

    // -------以下代码与MySQL实现保持一致.-------
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
