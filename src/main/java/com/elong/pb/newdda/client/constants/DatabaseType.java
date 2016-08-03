package com.elong.pb.newdda.client.constants;

import com.elong.pb.newdda.client.exception.DatabaseTypeUnsupportedException;

/**
 * Created by zhangyong on 2016/8/2.
 */
public enum DatabaseType {

    H2, MySQL, Oracle, SQLServer, DB2;

    public static DatabaseType valueFrom(final String databaseProductName) {
        try {
            return DatabaseType.valueOf(databaseProductName);
        } catch (final IllegalArgumentException ex) {
            throw new DatabaseTypeUnsupportedException(databaseProductName);
        }
    }

}
