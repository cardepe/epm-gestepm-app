package com.epm.gestepm.lib.executiontrace;

import java.util.Objects;
import com.epm.gestepm.lib.utils.TimeFormatUtils;

public interface ExecutionTimeProvider {

    void start();

    Long getStartExecutionTime();

    default Long getElapsedTimeFromNow() {

        final Long start = Objects.requireNonNullElse(getStartExecutionTime(), 0L);

        return System.currentTimeMillis() - start;
    }

    default String getHumanReadableStart() {
        return TimeFormatUtils.asHumanReadable(Objects.requireNonNullElse(getStartExecutionTime(), 0L));
    }

    default String getHumanReadableElapsed() {
        return TimeFormatUtils.asHumanReadable(Objects.requireNonNullElse(getElapsedTimeFromNow(), 0L));
    }

}
