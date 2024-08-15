package com.epm.gestepm.lib.applocale.model.dao.entity.finder;

import java.util.Objects;
import java.util.StringJoiner;

import com.epm.gestepm.lib.applocale.model.dao.constants.AppLocaleAttributes;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;

import static com.epm.gestepm.lib.applocale.model.dao.constants.AppLocaleAttributes.ATTR_AL_ID;

public class AppLocaleByIdFinder implements CollectableAttributes {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();
        map.put(ATTR_AL_ID, this.id);

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppLocaleByIdFinder that = (AppLocaleByIdFinder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AppLocaleByIdFinder.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
