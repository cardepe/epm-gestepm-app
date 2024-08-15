package com.epm.gestepm.masterdata.country.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.masterdata.country.dao.constants.CountryAttributes.*;

@Data
public class CountryUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String tag;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_C_ID, this.id);
        map.put(ATTR_C_NAME, this.name);
        map.put(ATTR_C_TAG, this.tag);

        return map;
    }

}
