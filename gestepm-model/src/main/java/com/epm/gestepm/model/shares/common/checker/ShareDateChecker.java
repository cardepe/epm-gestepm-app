package com.epm.gestepm.model.shares.common.checker;

import com.epm.gestepm.modelapi.shares.common.exception.StartEndDateException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class ShareDateChecker {

    private static final Integer MAX_HOURS = 8;

    public void checkStartBeforeEndDate(final LocalDateTime start, final LocalDateTime end) {
        if (end != null && start.isAfter(end)) {
            throw new StartEndDateException(start, end);
        }
    }

    public LocalDateTime checkMaxHours(final LocalDateTime startDate, final LocalDateTime endDate) {
        final LocalDateTime maxDateToEnd = startDate.plusHours(MAX_HOURS);

        return maxDateToEnd.isBefore(endDate) ? maxDateToEnd : endDate;
    }
}
