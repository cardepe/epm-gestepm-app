package com.epm.gestepm.lib.controller.metadata;

public class ResourceLocaleMetadata {

    private String currentResourceLocale;

    private String resourceInDefaultLocale;

    public String getCurrentResourceLocale() {
        return currentResourceLocale;
    }

    public void setCurrentResourceLocale(String currentResourceLocale) {
        this.currentResourceLocale = currentResourceLocale;
    }

    public String getResourceInDefaultLocale() {
        return resourceInDefaultLocale;
    }

    public void setResourceInDefaultLocale(String resourceInDefaultLocale) {
        this.resourceInDefaultLocale = resourceInDefaultLocale;
    }

}
