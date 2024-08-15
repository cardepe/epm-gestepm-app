package com.epm.gestepm.masterdata.country.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.masterdata.country.dao.constants.CountryAttributes.ATTR_C_NAME;
import static com.epm.gestepm.masterdata.country.dao.constants.CountryAttributes.ATTR_C_TAG;

@Data
public class CountryCreate implements CollectableAttributes {

    @NotNull
    private String name;

    @NotNull
    private String tag;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_C_NAME, this.name);
        map.put(ATTR_C_TAG, this.tag);

        return map;
    }

}
