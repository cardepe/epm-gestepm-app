package com.epm.gestepm.rest.displacement;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.masterdata.api.displacement.exception.DisplacementNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class DisplacementExceptionHandler extends BaseRestExceptionHandler {

    public static final int D_ERROR_CODE = 500;

    public static final String D_NOT_FOUND = "displacement-not-found";

    public DisplacementExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(DisplacementNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(DisplacementNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(D_ERROR_CODE, D_NOT_FOUND, D_NOT_FOUND, id);
    }

}
