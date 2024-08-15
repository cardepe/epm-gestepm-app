package com.epm.gestepm.lib.applocale.apimodel.exception;

public class DefaultAppLocaleNotFoundException extends RuntimeException {

    public DefaultAppLocaleNotFoundException() {
        super("Default app locale not found");
    }

}
