package com.epm.gestepm.lib.controller.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import com.epm.gestepm.lib.controller.error.APIError;
import com.epm.gestepm.lib.controller.error.I18nErrorMessageSource;
import com.epm.gestepm.lib.executiontrace.ExecutionRequestProvider;
import com.epm.gestepm.lib.security.exception.NotEnoughPermissionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

public class BaseRestExceptionHandler {

    private final ExecutionRequestProvider executionRequestProvider;

    private final I18nErrorMessageSource i18nErrorMessageSource;

    public BaseRestExceptionHandler(ExecutionRequestProvider executionRequestProvider,
            I18nErrorMessageSource i18nErrorMessageSource) {
        this.executionRequestProvider = executionRequestProvider;
        this.i18nErrorMessageSource = i18nErrorMessageSource;
    }

    protected String getI18nMsg(String msgKey) {
        return getI18nMsg(msgKey, new ArrayList<>());
    }

    protected String getI18nMsg(String msgKey, List<Object> params) {
        return i18nErrorMessageSource.getMessage(msgKey, params);
    }

    protected APIError getBaseApiError() {

        final APIError apiError = new APIError();
        apiError.setErrorTraceId(executionRequestProvider.getTraceId());

        return apiError;
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handleMissingHeader(final MissingRequestHeaderException ex) {

        final APIError apiError = toAPIError(0, "", ex.getMessage());

        final String objectName = ex.getHeaderName();
        apiError.putHelp("missingHeader", objectName);

        return apiError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handleMethodArgument(final MethodArgumentNotValidException ex) {

        final APIError apiError = toAPIError(0, "", ex.getMessage());

        final BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> allErrors = bindingResult.getFieldErrors();

        allErrors.stream().filter(e -> e.getArguments() != null).forEach(e -> {

            final String msg = StringUtils.capitalize(e.getDefaultMessage());
            final String objectName = e.getField();

            apiError.putHelp(objectName, msg);
        });

        return apiError;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handleConstraint(final ConstraintViolationException ex) {

        final APIError apiError = toAPIError(0, "", ex.getMessage());

        final Set<ConstraintViolation<?>> allErrors = ex.getConstraintViolations();

        allErrors.forEach(e -> {

            final String msg = StringUtils.capitalize(e.getMessage());
            final String objectName = e.getPropertyPath().toString();

            apiError.putHelp(objectName, msg);

        });

        return apiError;
    }

    @ExceptionHandler(NotEnoughPermissionException.class)
    @ResponseStatus(value = FORBIDDEN)
    public APIError handleConstraint(final NotEnoughPermissionException ex) {
        return toAPIError(0, "not-enough-permission", "not-enough-permission");
    }

    @ExceptionHandler(ValueInstantiationException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public APIError handle(final ValueInstantiationException ex) {

        final String typeName = ex.getType().getRawClass().getSimpleName();

        return toAPIError(0, "invalid-request-data", "invalid-request-data", typeName);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<APIError> handle(final HttpClientErrorException ex) {

        final String jsonError = ex.getResponseBodyAsString();
        final HttpStatus statusCode = ex.getStatusCode();

        final APIError resErrorDTO = mapStringToAPIError(jsonError);

        return new ResponseEntity<>(resErrorDTO, statusCode);
    }

    protected APIError toAPIError(int code, String titleKey, String detailKey, Object... params) {

        final List<Object> paramList = new ArrayList<>(Arrays.asList(params != null ? params : new Arrays[0]));

        final APIError apiError = getBaseApiError();
        apiError.setCode(code);
        apiError.setTitle(titleKey);
        apiError.setDetail(getI18nMsg(detailKey, paramList));

        return apiError;
    }

    private APIError mapStringToAPIError(final String json) {

        try {

            final ObjectMapper mapper = new ObjectMapper();
            final APIError errorResponseDTO = mapper.readValue(json, APIError.class);

            return errorResponseDTO;

        } catch (Exception ex) {
            return new APIError();
        }
    }

}
