package com.epm.gestepm.rest.shares.displacement;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.shares.displacement.exception.DisplacementShareForbiddenException;
import com.epm.gestepm.modelapi.shares.displacement.exception.DisplacementShareNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class DisplacementShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int DS_ERROR_CODE = 1400;

    public static final String DS_NOT_FOUND = "displacement-share-not-found";

    public static final String DS_FORBIDDEN = "displacement-share-forbidden";

    public DisplacementShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(DisplacementShareNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(DisplacementShareNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(DS_ERROR_CODE, DS_NOT_FOUND, DS_NOT_FOUND, id);
    }

    @ExceptionHandler(DisplacementShareForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(DisplacementShareForbiddenException ex) {

        final Integer id = ex.getId();
        final String message = DS_FORBIDDEN;

        return toAPIError(DS_ERROR_CODE, message, message, id);
    }
}
