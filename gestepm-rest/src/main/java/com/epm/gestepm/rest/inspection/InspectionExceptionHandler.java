package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class InspectionExceptionHandler extends BaseRestExceptionHandler {

    public static final int I_ERROR_CODE = 800;

    public static final String I_NOT_FOUND = "inspection-not-found";

    public InspectionExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(InspectionNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(InspectionNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(I_ERROR_CODE, I_NOT_FOUND, I_NOT_FOUND, id);
    }

}
