package com.epm.gestepm.model.project.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.ATTR_PR_ID;

@Data
public class ProjectDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PR_ID, this.id);

        return map;
    }

}
