package com.epm.gestepm.lib.applocale.apimodel.dto.filter;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;

public class AppLocaleFilterDto implements UsableAsCacheKey {

    private List<Integer> ids;

    private List<String> locales;

    private Boolean isDefault;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String asCacheKey() {

        final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();
        cacheKeyBuilder.addElement("ids", this.ids);
        cacheKeyBuilder.addElement("locales", this.locales);
        cacheKeyBuilder.addElement("isDefault", this.isDefault);

        return cacheKeyBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppLocaleFilterDto that = (AppLocaleFilterDto) o;
        return Objects.equals(ids, that.ids) && Objects.equals(locales, that.locales) && Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, locales, isDefault);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AppLocaleFilterDto.class.getSimpleName() + "[", "]")
                .add("ids=" + ids)
                .add("locales=" + locales)
                .add("isDefault=" + isDefault)
                .toString();
    }

}
