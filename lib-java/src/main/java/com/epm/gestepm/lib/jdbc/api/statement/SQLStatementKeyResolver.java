package com.epm.gestepm.lib.jdbc.api.statement;

import com.epm.gestepm.lib.jdbc.api.exception.MissingStatementForKeyException;

public interface SQLStatementKeyResolver {

    String resolve(final String statementKey) throws MissingStatementForKeyException;

}
