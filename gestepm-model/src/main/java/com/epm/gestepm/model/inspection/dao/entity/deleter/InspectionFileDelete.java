package com.epm.gestepm.model.inspection.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes.ATTR_IF_ID;

@Data
public class InspectionFileDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_IF_ID, this.id);

        return map;
    }

}
