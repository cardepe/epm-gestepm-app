package com.epm.gestepm.rest.projectmaterial;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.projectmaterial.exception.ProjectMaterialNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ProjectMaterialExceptionHandler extends BaseRestExceptionHandler {

    public static final int PRMAT_ERROR_CODE = 2100;

    public static final String PRMAT_NOT_FOUND = "project-material-not-found";

    public ProjectMaterialExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ProjectMaterialNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ProjectMaterialNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PRMAT_ERROR_CODE, PRMAT_NOT_FOUND, PRMAT_NOT_FOUND, id);
    }

}
