package com.epm.gestepm.lib.service;

import com.epm.gestepm.lib.applocale.apimodel.dto.AppLocaleDto;
import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;

public abstract class LocalizedService {

    private final AppLocaleService appLocaleService;

    protected LocalizedService(AppLocaleService appLocaleService) {
        this.appLocaleService = appLocaleService;
    }

    protected AppLocaleDto getDefaultAppLocale() {
        return this.appLocaleService.findDefaultOrNotFound();
    }

    protected String getDefaultLocaleISO() {
        return getDefaultAppLocale().getAppLocale();
    }

}
