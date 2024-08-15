package com.epm.gestepm.lib.jdbc.impl.statement;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.epm.gestepm.lib.jdbc.api.exception.MissingStatementForKeyException;
import com.epm.gestepm.lib.jdbc.api.statement.SQLStatementKeyResolver;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;

@EnableExecutionLog(layerMarker = DELEGATOR)
public class DefaultSQLStatementKeyResolver implements SQLStatementKeyResolver {

    private final Map<String, String> keyStatementMap;

    public DefaultSQLStatementKeyResolver(Map<String, String> keyStatementMap) {
        this.keyStatementMap = new HashMap<>();
        this.keyStatementMap.putAll(Objects.requireNonNullElseGet(keyStatementMap, HashMap::new));
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            debugOut = true,
            level = "TRACE",
            msgIn = "Resolving sql statement for key",
            msgOut = "Resolved sql statement for key",
            errorMsg = "Error resolving sql statement key")
    public String resolve(final String statementKey) throws MissingStatementForKeyException {

        if (keyStatementMap.containsKey(statementKey)) {
            return keyStatementMap.get(statementKey);
        }

        throw new MissingStatementForKeyException(statementKey);
    }

}
