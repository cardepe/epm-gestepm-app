package com.epm.gestepm.rest.personalexpensesheet;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetExportException;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class PersonalExpenseSheetExceptionHandler extends BaseRestExceptionHandler {

    public static final int PES_ERROR_CODE = 1000;

    public static final String PES_NOT_FOUND = "personal-expense-sheet-not-found";

    public static final String PES_EXPORT_EXCEPTION = "personal-expense-sheet-export-exception";

    public PersonalExpenseSheetExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(PersonalExpenseSheetNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(PersonalExpenseSheetNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PES_ERROR_CODE, PES_NOT_FOUND, PES_NOT_FOUND, id);
    }

    @ExceptionHandler(PersonalExpenseSheetExportException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public APIError handle(PersonalExpenseSheetExportException ex) {

        final Integer id = ex.getId();
        final String message = ex.getErrorMessage();

        return toAPIError(PES_ERROR_CODE, PES_EXPORT_EXCEPTION, PES_EXPORT_EXCEPTION, id, message);
    }

}
