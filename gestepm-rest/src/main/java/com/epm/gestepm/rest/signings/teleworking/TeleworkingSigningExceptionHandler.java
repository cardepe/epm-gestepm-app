package com.epm.gestepm.rest.signings.teleworking;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.signings.teleworking.exception.TeleworkingSigningNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class TeleworkingSigningExceptionHandler extends BaseRestExceptionHandler {

    public static final int TS_ERROR_CODE = 1300;

    public static final String TS_NOT_FOUND = "teleworking-signing-not-found";

    public TeleworkingSigningExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(TeleworkingSigningNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(TeleworkingSigningNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(TS_ERROR_CODE, TS_NOT_FOUND, TS_NOT_FOUND, id);
    }

}
