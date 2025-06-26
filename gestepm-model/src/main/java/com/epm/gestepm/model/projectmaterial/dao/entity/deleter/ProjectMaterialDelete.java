package com.epm.gestepm.model.projectmaterial.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialAttributes.ATTR_PRMAT_ID;

@Data
public class ProjectMaterialDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PRMAT_ID, this.id);

        return map;
    }

}
