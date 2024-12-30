package com.epm.gestepm.lib.testutils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.epm.gestepm.lib.executiontrace.ExecutionTimeProvider;

public class MockExecutionTimeProvider implements ExecutionTimeProvider {

    @Override
    public void start() {
        // N/A
    }

    @Override
    public Long getStartExecutionTime() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

}
