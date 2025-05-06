package com.epm.gestepm.rest.shares.work;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotEndedException;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareExportException;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareForbiddenException;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class WorkShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int WS_ERROR_CODE = 1800;

    public static final String WS_NOT_FOUND = "work-share-not-found";

    public static final String WS_NOT_ENDED = "work-share-not-ended";
    
    public static final String WS_FORBIDDEN = "work-share-forbidden";

    public static final String WS_FILE_NOT_FOUND = "work-share-file-not-found";

    public static final String WS_EXPORT_EXCEPTION = "work-share-export-exception";
    
    public WorkShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(WorkShareNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(WorkShareNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(WS_ERROR_CODE, WS_NOT_FOUND, WS_NOT_FOUND, id);
    }

    @ExceptionHandler(InspectionNotEndedException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(InspectionNotEndedException ex) {

        final Integer id = ex.getId();

        return toAPIError(WS_ERROR_CODE, WS_NOT_ENDED, WS_NOT_ENDED, id);
    }

    @ExceptionHandler(WorkShareForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(WorkShareForbiddenException ex) {

        final Integer id = ex.getId();
        final String message = WS_FORBIDDEN;

        return toAPIError(WS_ERROR_CODE, message, message, id);
    }

    @ExceptionHandler(WorkShareFileNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(WorkShareFileNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(WS_ERROR_CODE, WS_FILE_NOT_FOUND, WS_FILE_NOT_FOUND, id);
    }

    @ExceptionHandler(WorkShareExportException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public APIError handle(WorkShareExportException ex) {

        final Integer id = ex.getId();

        return toAPIError(WS_ERROR_CODE, WS_EXPORT_EXCEPTION, WS_EXPORT_EXCEPTION, id);
    }
}
