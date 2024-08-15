package com.epm.gestepm.rest.country;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.masterdata.api.country.exception.CountryNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CountryExceptionHandler extends BaseRestExceptionHandler {

    public static final int C_ERROR_CODE = 200;

    public static final String C_NOT_FOUND = "country-not-found";

    public CountryExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(CountryNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(C_ERROR_CODE, C_NOT_FOUND, C_NOT_FOUND, id);
    }

}
