package com.epm.gestepm.model.shares.displacement.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareAttributes.*;

@Data
public class DisplacementShareCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String observations;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer createdBy;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_DI_U_ID, this.userId);
        map.put(ATTR_DI_P_ID, this.projectId);
        map.put(ATTR_DI_DESCRIPTION, this.description);
        map.put(ATTR_DI_START_DATE, this.startDate);
        map.put(ATTR_DI_END_DATE, this.endDate);
        map.put(ATTR_DI_OBSERVATIONS, this.observations);
        map.putTimestamp(ATTR_DI_CREATED_AT, this.createdAt);
        map.put(ATTR_DI_CREATED_BY, this.createdBy);

        return map;
    }
}
