package com.epm.gestepm.rest.shares.programmed;

import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.controller.exception.BaseRestExceptionHandler;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotEndedException;
import com.epm.gestepm.modelapi.shares.programmed.exception.ProgrammedShareExportException;
import com.epm.gestepm.modelapi.shares.programmed.exception.ProgrammedShareFileNotFoundException;
import com.epm.gestepm.modelapi.shares.programmed.exception.ProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.shares.programmed.exception.ProgrammedShareNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ProgrammedShareExceptionHandler extends BaseRestExceptionHandler {

    public static final int PS_ERROR_CODE = 1700;

    public static final String PS_NOT_FOUND = "programmed-share-not-found";

    public static final String PS_NOT_ENDED = "programmed-share-not-ended";
    
    public static final String PS_FORBIDDEN = "programmed-share-forbidden";

    public static final String PS_FILE_NOT_FOUND = "programmed-share-file-not-found";

    public static final String PS_EXPORT_EXCEPTION = "programmed-share-export-exception";
    
    public ProgrammedShareExceptionHandler(ExecutionRequestProvider executionRequestProvider, I18nErrorMessageSource i18nErrorMessageSource) {
        super(executionRequestProvider, i18nErrorMessageSource);
    }

    @ExceptionHandler(ProgrammedShareNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ProgrammedShareNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PS_ERROR_CODE, PS_NOT_FOUND, PS_NOT_FOUND, id);
    }

    @ExceptionHandler(InspectionNotEndedException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(InspectionNotEndedException ex) {

        final Integer id = ex.getId();

        return toAPIError(PS_ERROR_CODE, PS_NOT_ENDED, PS_NOT_ENDED, id);
    }

    @ExceptionHandler(ProgrammedShareForbiddenException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handle(ProgrammedShareForbiddenException ex) {

        final Integer id = ex.getId();
        final String message = PS_FORBIDDEN;

        return toAPIError(PS_ERROR_CODE, message, message, id);
    }

    @ExceptionHandler(ProgrammedShareFileNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    public APIError handle(ProgrammedShareFileNotFoundException ex) {

        final Integer id = ex.getId();

        return toAPIError(PS_ERROR_CODE, PS_FILE_NOT_FOUND, PS_FILE_NOT_FOUND, id);
    }

    @ExceptionHandler(ProgrammedShareExportException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public APIError handle(ProgrammedShareExportException ex) {

        final Integer id = ex.getId();

        return toAPIError(PS_ERROR_CODE, PS_EXPORT_EXCEPTION, PS_EXPORT_EXCEPTION, id);
    }
}
