package com.epm.gestepm.rest.holiday;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.masterdata.api.displacement.exception.DisplacementNotFoundException;
import com.epm.gestepm.masterdata.api.holiday.exception.HolidayNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class HolidayExceptionHandler extends BaseRestExceptionHandler {

    public static final int H_ERROR_CODE = 600;

    public static final String H_NOT_FOUND = "holiday-not-found";

    public HolidayExceptionHandler(final ExecutionRequestProvider executionRequestProvider, final I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(HolidayNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(HolidayNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(H_ERROR_CODE, H_NOT_FOUND, H_NOT_FOUND, id);
    }

}
