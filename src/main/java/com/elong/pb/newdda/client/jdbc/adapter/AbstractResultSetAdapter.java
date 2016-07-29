/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.elong.pb.newdda.client.jdbc.adapter;
import com.elong.pb.newdda.client.jdbc.operation.AbstractUnsupportedOperationResultSet;
import com.google.common.base.Preconditions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractResultSetAdapter extends AbstractUnsupportedOperationResultSet {

    private final List<ResultSet> resultSets;

    private boolean closed;

    public AbstractResultSetAdapter(final List<ResultSet> resultSets) throws SQLException {
        Preconditions.checkArgument(!resultSets.isEmpty());
        this.resultSets = resultSets;
    }

    @Override
    public final void close() throws SQLException {
        for (ResultSet each : resultSets) {
            each.close();
        }
        closed = true;
    }

    @Override
    public final boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public final void setFetchDirection(final int direction) throws SQLException {
        for (ResultSet each : resultSets) {
            each.setFetchDirection(direction);
        }
    }

    @Override
    public final void setFetchSize(final int rows) throws SQLException {
        for (ResultSet each : resultSets) {
            each.setFetchSize(rows);
        }
    }

}
