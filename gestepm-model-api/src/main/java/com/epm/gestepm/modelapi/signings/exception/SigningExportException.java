package com.epm.gestepm.modelapi.signings.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SigningExportException extends RuntimeException {

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final Integer userId;

    private final String message;

}
