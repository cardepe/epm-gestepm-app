package com.epm.gestepm.model.projectmaterial.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialAttributes.*;

@Data
public class ProjectMaterialCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer projectId;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PRMAT_PROJECT_ID, this.projectId);
        map.put(ATTR_PRMAT_NAME_ES, this.nameEs);
        map.put(ATTR_PRMAT_NAME_FR, this.nameFr);
        map.put(ATTR_PRMAT_REQUIRED, this.required);

        return map;
    }

}
