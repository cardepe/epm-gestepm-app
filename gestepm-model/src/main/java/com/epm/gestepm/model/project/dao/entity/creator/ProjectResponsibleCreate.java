package com.epm.gestepm.model.project.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.*;
import static com.epm.gestepm.model.user.dao.constants.UserAttributes.ATTR_U_ID;

@Data
@AllArgsConstructor
public class ProjectResponsibleCreate implements CollectableAttributes {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PR_ID, this.projectId);
        map.put(ATTR_U_ID, this.userId);

        return map;
    }
}
