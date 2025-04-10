package com.epm.gestepm.model.shares.displacement.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditClose;
import com.epm.gestepm.lib.audit.AuditUpdate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareAttributes.*;

@Data
public class DisplacementShareUpdate implements AuditUpdate, CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private String observations;

    private LocalDateTime updatedAt;

    private Integer updatedBy;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_DI_ID, this.id);
        map.put(ATTR_DI_P_ID, this.projectId);
        map.put(ATTR_DI_DESCRIPTION, this.description);
        map.put(ATTR_DI_START_DATE, this.startDate);
        map.put(ATTR_DI_END_DATE, this.endDate);
        map.put(ATTR_DI_OBSERVATIONS, this.observations);
        map.put(ATTR_DI_MODIFIED_AT, this.updatedAt);
        map.put(ATTR_DI_MODIFIED_BY, this.updatedBy);

        return map;
    }

}
