package com.epm.gestepm.rest.personalexpense;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.personalexpense.exception.PersonalExpenseNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class PersonalExpenseExceptionHandler extends BaseRestExceptionHandler {

    public static final int PE_ERROR_CODE = 1100;

    public static final String PE_NOT_FOUND = "personal-expense-not-found";

    public PersonalExpenseExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(PersonalExpenseNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(PersonalExpenseNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PE_ERROR_CODE, PE_NOT_FOUND, PE_NOT_FOUND, id);
    }

}
