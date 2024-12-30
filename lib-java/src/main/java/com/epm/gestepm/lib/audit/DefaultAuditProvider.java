package com.epm.gestepm.lib.audit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;
import com.epm.gestepm.lib.user.UserProvider;
import com.epm.gestepm.lib.user.data.UserLogin;

public class DefaultAuditProvider implements AuditProvider {

    private final UserProvider userProvider;

    private final ExecutionTimeProvider executionTimeProvider;

    public DefaultAuditProvider(UserProvider userProvider, ExecutionTimeProvider executionTimeProvider) {
        this.userProvider = userProvider;
        this.executionTimeProvider = executionTimeProvider;
    }

    @Override
    public String login() {

        final UserLogin login = userProvider.getOrFail(UserLogin.class);

        return login.getValue();
    }

    @Override
    public LocalDateTime time() {
        final Long millis = executionTimeProvider.getStartExecutionTime();
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

}
