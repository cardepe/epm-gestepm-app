package com.epm.gestepm.lib.applocale.model.dao.entity.filter;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import com.epm.gestepm.lib.applocale.model.dao.constants.AppLocaleAttributes;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;

import static com.epm.gestepm.lib.applocale.model.dao.constants.AppLocaleAttributes.*;

public class AppLocaleFilter implements CollectableAttributes {

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
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();
        map.putList(ATTR_AL_IDS, this.ids);
        map.putListWithTransform(ATTR_AL_LOCALES, this.locales, String::toUpperCase);
        map.putBooleanAsInt(ATTR_AL_IS_DEFAULT, this.isDefault);

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppLocaleFilter that = (AppLocaleFilter) o;
        return Objects.equals(ids, that.ids) && Objects.equals(locales, that.locales) && Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, locales, isDefault);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AppLocaleFilter.class.getSimpleName() + "[", "]")
                .add("ids=" + ids)
                .add("locales=" + locales)
                .add("isDefault=" + isDefault)
                .toString();
    }

}
