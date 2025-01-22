package com.epm.gestepm.rest.personalsigning;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.personalsigning.exception.PersonalSigningForbiddenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class PersonalSigningExceptionHandler extends BaseRestExceptionHandler {

    public static final int PS_ERROR_CODE = 1200;

    public static final String PS_FORBIDDEN = "personal-signing-forbidden";

    public PersonalSigningExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(PersonalSigningForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(PersonalSigningForbiddenException ex) {

        final Integer userId = ex.getUserId();

        return toAPIError(PS_ERROR_CODE, PS_FORBIDDEN, PS_FORBIDDEN, userId);
    }
}
