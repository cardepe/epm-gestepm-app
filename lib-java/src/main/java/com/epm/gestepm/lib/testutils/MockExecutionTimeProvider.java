package com.epm.gestepm.lib.testutils;

import java.time.OffsetDateTime;
import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;

public class MockExecutionTimeProvider implements ExecutionTimeProvider {

    @Override
    public void start() {
        // N/A
    }

    @Override
    public Long getStartExecutionTime() {
        return OffsetDateTime.now().toEpochSecond();
    }

}
