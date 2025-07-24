package com.epm.gestepm.rest;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CommonExceptionHandler extends BaseRestExceptionHandler {

    public static final int COMMON_ERROR_CODE = 2200;

    public static final String UNSUPPORTED_OPERATION = "unsupported-operation";

    public CommonExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(UnsupportedOperationException ex) {
        final String message = ex.getMessage();

        return toAPIError(COMMON_ERROR_CODE, UNSUPPORTED_OPERATION, UNSUPPORTED_OPERATION, message);
    }
}