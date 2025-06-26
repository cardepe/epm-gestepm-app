package com.epm.gestepm.rest.customer;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.customer.exception.CustomerNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomerExceptionHandler extends BaseRestExceptionHandler {

    public static final int CU_ERROR_CODE = 2000;

    public static final String CU_NOT_FOUND = "customer-not-found";

    public CustomerExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(CustomerNotFoundException ex) {

        final Integer projectId = ex.getProjectId();

        return toAPIError(CU_ERROR_CODE, CU_NOT_FOUND, CU_NOT_FOUND, projectId);
    }

}
