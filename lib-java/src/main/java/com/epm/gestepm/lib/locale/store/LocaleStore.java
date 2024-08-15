package com.epm.gestepm.lib.locale.store;

import java.util.Optional;

public class LocaleStore {

    private String locale;

    public Optional<String> getLocale() {
        return Optional.ofNullable(locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
