package com.elong.pb.newdda.client.exception;

public class DatabaseTypeUnsupportedException extends RuntimeException {

    private static final long serialVersionUID = -7807395469148925091L;

    private static final String MESSAGE = "Can not support database type [%s].";

    public DatabaseTypeUnsupportedException(final String databaseType) {
        super(String.format(MESSAGE, databaseType));
    }
    
}
