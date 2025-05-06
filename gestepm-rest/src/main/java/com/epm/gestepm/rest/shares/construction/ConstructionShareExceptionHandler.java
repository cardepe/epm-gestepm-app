package com.epm.gestepm.rest.shares.construction;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotEndedException;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareExportException;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareForbiddenException;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ConstructionShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int CS_ERROR_CODE = 1600;

    public static final String CS_NOT_FOUND = "construction-share-not-found";

    public static final String CS_NOT_ENDED = "construction-share-not-ended";
    
    public static final String CS_FORBIDDEN = "construction-share-forbidden";

    public static final String CS_FILE_NOT_FOUND = "construction-share-file-not-found";

    public static final String CS_EXPORT_EXCEPTION = "construction-share-export-exception";
    
    public ConstructionShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ConstructionShareNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ConstructionShareNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(CS_ERROR_CODE, CS_NOT_FOUND, CS_NOT_FOUND, id);
    }

    @ExceptionHandler(InspectionNotEndedException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(InspectionNotEndedException ex) {

        final Integer id = ex.getId();

        return toAPIError(CS_ERROR_CODE, CS_NOT_ENDED, CS_NOT_ENDED, id);
    }

    @ExceptionHandler(ConstructionShareForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(ConstructionShareForbiddenException ex) {

        final Integer id = ex.getId();
        final String message = CS_FORBIDDEN;

        return toAPIError(CS_ERROR_CODE, message, message, id);
    }

    @ExceptionHandler(ConstructionShareFileNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ConstructionShareFileNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(CS_ERROR_CODE, CS_FILE_NOT_FOUND, CS_FILE_NOT_FOUND, id);
    }

    @ExceptionHandler(ConstructionShareExportException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public APIError handle(ConstructionShareExportException ex) {

        final Integer id = ex.getId();

        return toAPIError(CS_ERROR_CODE, CS_EXPORT_EXCEPTION, CS_EXPORT_EXCEPTION, id);
    }
}
