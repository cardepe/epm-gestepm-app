package com.epm.gestepm.lib.locale;

import java.util.Optional;

public interface LocaleProvider {

    Optional<String> getLocale();

    void setLocale(final String locale);

}
