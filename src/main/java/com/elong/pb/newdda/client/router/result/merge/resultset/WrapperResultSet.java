package com.elong.pb.newdda.client.router.result.merge.resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

/**
 * resultset 默认封装
 * Created by zhangyong on 2016/10/9.
 */
public class WrapperResultSet extends AbstractDelegateResultSet {

    public WrapperResultSet(final ResultSet resultSetWhenNextOnce) throws SQLException {
        super(Collections.singletonList(resultSetWhenNextOnce));
    }

    @Override
    protected boolean firstNext() throws SQLException {
        return true;
    }

    @Override
    protected boolean afterFirstNext() throws SQLException {
        return getDelegate().next();
    }

}
