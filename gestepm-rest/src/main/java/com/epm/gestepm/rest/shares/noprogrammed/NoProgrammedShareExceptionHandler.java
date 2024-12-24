package com.epm.gestepm.rest.shares.noprogrammed;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.masterdata.api.country.exception.CountryNotFoundException;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class NoProgrammedShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int NPS_ERROR_CODE = 300;

    public static final String NPS_NOT_FOUND = "no-programmed-share-not-found";

    public NoProgrammedShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(NoProgrammedShareNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(NoProgrammedShareNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(NPS_ERROR_CODE, NPS_NOT_FOUND, NPS_NOT_FOUND, id);
    }

}
