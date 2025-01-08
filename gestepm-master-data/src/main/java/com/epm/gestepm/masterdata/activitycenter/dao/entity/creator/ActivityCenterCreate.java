package com.epm.gestepm.masterdata.activitycenter.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.masterdata.activitycenter.dao.constants.ActivityCenterAttributes.ATTR_AC_COUNTRY_ID;
import static com.epm.gestepm.masterdata.activitycenter.dao.constants.ActivityCenterAttributes.ATTR_AC_NAME;

@Data
public class ActivityCenterCreate implements CollectableAttributes {

    @NotNull
    private String name;

    @NotNull
    private Integer countryId;
    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_AC_NAME, this.name);
        map.put(ATTR_AC_COUNTRY_ID, this.countryId);

        return map;
    }

}
