package com.epm.gestepm.modelapi.shares.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StartEndDateException extends RuntimeException {

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}