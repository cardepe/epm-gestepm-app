package com.epm.gestepm.rest.user;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class UserExceptionHandler extends BaseRestExceptionHandler {

    public static final int U_ERROR_CODE = 900;

    public static final String U_NOT_FOUND = "user-not-found";

    public UserExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(UserByIdNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(UserByIdNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(U_ERROR_CODE, U_NOT_FOUND, U_NOT_FOUND, id);
    }

}
