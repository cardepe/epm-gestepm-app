package com.epm.gestepm.model.personalexpensesheet.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
public class PersonalExpenseSheetCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @NotNull
    private PersonalExpenseSheetStatusEnum status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer createdBy;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);
        map.putEnum(ATTR_PES_STATUS, this.status);
        map.putTimestamp(ATTR_PES_CREATED_AT, this.createdAt);
        map.put(ATTR_PES_CREATED_BY, this.createdBy);

        return map;
    }

}
