package com.epm.gestepm.lib.jdbc.api.exception;

public class MissingStatementForKeyException extends SQLStatementKeyException {

    public MissingStatementForKeyException(String statementKey) {
        super(statementKey);
    }

}
