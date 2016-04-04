package com.elong.pb.newdda.jdbc.adapter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/3/30
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDataSourceAdapter extends WrapperAdapter implements DataSource {

    private PrintWriter logWriter = new PrintWriter(System.out);

    @Override
    public final PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public final void setLogWriter(final PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public final int getLoginTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported getLoginTimeout()");
    }

    @Override
    public final void setLoginTimeout(final int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported setLoginTimeout(int seconds)");
    }

    //=================================== special method for jdk 1.7 start ===================================
    @Override
    public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
    //=================================== special method for jdk 1.7 end ===================================

}
