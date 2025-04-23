package com.epm.gestepm.rest.signings;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.signings.exception.SigningForbiddenException;
import com.epm.gestepm.modelapi.signings.teleworking.exception.TeleworkingSigningNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class SigningExceptionHandler extends BaseRestExceptionHandler {

    public static final int SI_ERROR_CODE = 1400;

    public static final String SI_FORBIDDEN = "signing-forbidden";

    public SigningExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(SigningForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(SigningForbiddenException ex) {

        final Integer id = ex.getId();

        return toAPIError(SI_ERROR_CODE, SI_FORBIDDEN, SI_FORBIDDEN, id);
    }

}
