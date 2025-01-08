package com.epm.gestepm.masterdata.displacement.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.masterdata.displacement.dao.entity.DisplacementType;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementAttributes.*;
import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementAttributes.ATTR_D_TOTAL_TIME;

@Data
public class DisplacementUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private String name;

    @NotNull
    private DisplacementType type;

    @NotNull
    private Integer totalTime;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_D_ID, this.id);
        map.put(ATTR_D_AC_ID, this.activityCenterId);
        map.put(ATTR_D_NAME, this.name);
        map.putEnum(ATTR_D_TYPE, this.type);
        map.put(ATTR_D_TOTAL_TIME, this.totalTime);

        return map;
    }

}
