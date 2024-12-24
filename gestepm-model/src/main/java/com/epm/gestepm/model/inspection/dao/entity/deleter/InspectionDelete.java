package com.epm.gestepm.model.inspection.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(InspectionAttributes.ATTR_I_ID, this.id);

        return map;
    }

}
