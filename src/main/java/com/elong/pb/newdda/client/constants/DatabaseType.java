package com.elong.pb.newdda.client.constants;

import com.elong.pb.newdda.client.exception.DatabaseTypeUnsupportedException;

/**
 * Created by zhangyong on 2016/8/2.
 */
public enum DatabaseType {

    H2, MySQL, Oracle, SQLServer, DB2;

    public static DatabaseType valueFrom(final String databaseName) {
        try {
            return DatabaseType.valueOf(databaseName);
        } catch (final IllegalArgumentException ex) {
            throw new DatabaseTypeUnsupportedException(databaseName);
        }
    }

}
