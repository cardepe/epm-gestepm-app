package com.epm.gestepm.lib.jdbc.api.exception;

public class SQLStatementKeyException extends RuntimeException {

    private final String statementKey;

    public SQLStatementKeyException(String statementKey) {
        this.statementKey = statementKey;
    }

    public String getStatementKey() {
        return statementKey;
    }

}
