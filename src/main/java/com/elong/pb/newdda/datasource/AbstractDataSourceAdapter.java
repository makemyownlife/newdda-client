package com.elong.pb.newdda.datasource;

import com.elong.pb.newdda.jdbc.adapter.WrapperAdapter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

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

}
