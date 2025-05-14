package com.epm.gestepm.rest.shares;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.shares.common.exception.StartEndDateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int SH_ERROR_CODE = 1900;

    public static final String SH_START_END_DATE_EXCEPTION = "start-end-date-exception";

    public ShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(StartEndDateException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handle(StartEndDateException ex) {

        final LocalDateTime startDate = ex.getStartDate();
        final LocalDateTime endDate = ex.getEndDate();

        return toAPIError(SH_ERROR_CODE, SH_START_END_DATE_EXCEPTION, SH_START_END_DATE_EXCEPTION, startDate, endDate);
    }

}
