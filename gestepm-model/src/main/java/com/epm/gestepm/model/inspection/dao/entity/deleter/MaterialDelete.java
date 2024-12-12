package com.epm.gestepm.model.inspection.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.constants.MaterialAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MaterialDelete implements CollectableAttributes {

    @NotNull
    private Integer inspectionId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(MaterialAttributes.ATTR_M_INSPECTION_ID, this.inspectionId);

        return map;
    }

}
