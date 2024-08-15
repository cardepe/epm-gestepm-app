package com.epm.gestepm.lib.applocale.apimodel.dto.finder;

import java.util.Objects;
import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import javax.validation.constraints.NotNull;

public class AppLocaleByIdFinderDto implements UsableAsCacheKey {

    @NotNull
    private Integer appLocaleId;

    public Integer getAppLocaleId() {
        return this.appLocaleId;
    }

    public void setAppLocaleId(Integer appLocaleId) {
        this.appLocaleId = appLocaleId;
    }

    @Override
    public String asCacheKey() {

        final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.addElement("appLocaleId", this.appLocaleId);

        return cacheKeyBuilder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.appLocaleId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppLocaleByIdFinderDto)) {
            return false;
        }
        final AppLocaleByIdFinderDto other = (AppLocaleByIdFinderDto) obj;
        return Objects.equals(this.appLocaleId, other.appLocaleId);
    }

    @Override
    public String toString() {
        return "AppLocaleByIdFinderDto [appLocaleId=" + this.appLocaleId + "]";
    }

}
