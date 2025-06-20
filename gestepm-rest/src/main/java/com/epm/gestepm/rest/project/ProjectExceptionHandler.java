package com.epm.gestepm.rest.project;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.deprecated.project.exception.ProjectIsNotStationException;
import com.epm.gestepm.modelapi.deprecated.project.exception.ProjectByIdNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ProjectExceptionHandler extends BaseRestExceptionHandler {

    public static final int PR_ERROR_CODE = 700;

    public static final String PR_NOT_FOUND = "project-not-found";
    
    public static final String PR_IS_NOT_S_FOUND = "project-is-not-station";

    public ProjectExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ProjectByIdNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ProjectByIdNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PR_ERROR_CODE, PR_NOT_FOUND, PR_NOT_FOUND, id);
    }
    
    @ExceptionHandler(ProjectIsNotStationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handle(ProjectIsNotStationException ex) {
        final Integer id = ex.getId();

        return toAPIError(PR_ERROR_CODE, PR_IS_NOT_S_FOUND, PR_IS_NOT_S_FOUND, id);
    }
}
