package com.epm.gestepm.rest.activitycenter;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.masterdata.api.activitycenter.exception.ActivityCenterNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ActivityCenterExceptionHandler extends BaseRestExceptionHandler {

    public static final int AC_ERROR_CODE = 400;

    public static final String AC_NOT_FOUND = "activity-center-not-found";

    public ActivityCenterExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ActivityCenterNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ActivityCenterNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(AC_ERROR_CODE, AC_NOT_FOUND, AC_NOT_FOUND, id);
    }

}
