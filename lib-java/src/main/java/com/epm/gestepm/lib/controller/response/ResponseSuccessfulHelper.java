package com.epm.gestepm.lib.controller.response;

import java.util.Locale;
import com.epm.gestepm.lib.locale.LocaleProvider;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

public class ResponseSuccessfulHelper {

    private static final String DEFAULT_TITLE_PROPERTY = "successful-operation";

    private final LocaleProvider localeProvider;

    private final MessageSource errorSource;

    public ResponseSuccessfulHelper(final LocaleProvider localeProvider, final MessageSource errorSource) {
        this.localeProvider = localeProvider;
        this.errorSource = errorSource;
    }

    public ResSuccess buildSuccessfulResponse() {

        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("NONE"));
        final String detail = this.errorSource.getMessage(DEFAULT_TITLE_PROPERTY, null, locale);

        final ResSuccess response = new ResSuccess();
        response.setHttpStatus(HttpStatus.OK.value());
        response.setTitle(DEFAULT_TITLE_PROPERTY);
        response.setDetail(detail);

        return response;
    }

}
