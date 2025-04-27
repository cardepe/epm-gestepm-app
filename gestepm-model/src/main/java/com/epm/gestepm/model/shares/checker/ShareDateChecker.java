package com.epm.gestepm.model.shares.checker;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class ShareDateChecker {

    private static final Integer MAX_HOURS = 8;

    public LocalDateTime checker(final LocalDateTime startDate, final LocalDateTime endDate) {
        final LocalDateTime maxDateToEnd = startDate.plusHours(MAX_HOURS);

        return maxDateToEnd.isBefore(endDate) ? maxDateToEnd : endDate;
    }
}
