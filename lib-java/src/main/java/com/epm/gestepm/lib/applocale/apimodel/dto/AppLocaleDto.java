package com.epm.gestepm.lib.applocale.apimodel.dto;

import java.io.Serializable;
import java.util.Objects;

public class AppLocaleDto implements Serializable {

    private Integer appLocaleId;

    private String appLocale;

    private String name;

    private Boolean isDefault;

    public Integer getAppLocaleId() {
        return this.appLocaleId;
    }

    public void setAppLocaleId(final Integer appLocaleId) {
        this.appLocaleId = appLocaleId;
    }

    public String getAppLocale() {
        return this.appLocale;
    }

    public void setAppLocale(final String appLocale) {
        this.appLocale = appLocale;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(final Boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.appLocale, this.appLocaleId, this.isDefault, this.name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppLocaleDto)) {
            return false;
        }
        final AppLocaleDto other = (AppLocaleDto) obj;
        return Objects.equals(this.appLocale, other.appLocale)
                && Objects.equals(this.appLocaleId, other.appLocaleId)
                && Objects.equals(this.isDefault, other.isDefault) && Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "AppLocaleDto [appLocaleId="
                + this.appLocaleId + ", appLocale=" + this.appLocale + ", name=" + this.name
                + ", isDefault=" + this.isDefault + "]";
    }

}
