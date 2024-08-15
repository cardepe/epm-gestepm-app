package com.epm.gestepm.lib.controller.error;

import java.util.List;
import java.util.Locale;
import com.epm.gestepm.lib.locale.LocaleProvider;
import org.springframework.context.MessageSource;

public class I18nErrorMessageSource {

    private final MessageSource errorSource;

    private final LocaleProvider localeProvider;

    public I18nErrorMessageSource(final MessageSource errorSource, final LocaleProvider localeProvider) {

        this.errorSource = errorSource;
        this.localeProvider = localeProvider;
    }

    public String getMessage(final String msgKey, final List<Object> params) {

        final String requestLocale = this.localeProvider.getLocale().orElse("EN");

        return this.errorSource.getMessage(msgKey, params.toArray(), Locale.forLanguageTag(requestLocale));
    }

}
