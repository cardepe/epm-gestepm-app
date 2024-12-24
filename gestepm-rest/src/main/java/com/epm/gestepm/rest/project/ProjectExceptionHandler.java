package com.epm.gestepm.rest.project;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.project.exception.ProjectIsNotStationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ProjectExceptionHandler extends BaseRestExceptionHandler {

    public static final int P_ERROR_CODE = 700;

    public static final String P_IS_NOT_S_FOUND = "project-is-not-station";

    public ProjectExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ProjectIsNotStationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handle(ProjectIsNotStationException ex) {
        final Integer id = ex.getId();

        return toAPIError(P_ERROR_CODE, P_IS_NOT_S_FOUND, P_IS_NOT_S_FOUND, id);
    }
}
