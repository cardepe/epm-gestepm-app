package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.constants.MaterialAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MaterialCreate implements CollectableAttributes {

    private Integer inspectionId;

    @NotNull
    private String description;

    @NotNull
    private Integer units;

    @NotNull
    private String reference;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(MaterialAttributes.ATTR_M_INSPECTION_ID, this.inspectionId);
        map.put(MaterialAttributes.ATTR_M_DESCRIPTION, this.description);
        map.put(MaterialAttributes.ATTR_M_UNITS, this.units);
        map.put(MaterialAttributes.ATTR_M_REFERENCE, this.reference);

        return map;
    }
}
