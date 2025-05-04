package com.epm.gestepm.model.shares.programmed.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareAttributes.*;

@Data
public class ProgrammedShareCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer createdBy;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PS_U_ID, this.userId);
        map.put(ATTR_PS_P_ID, this.projectId);
        map.putTimestamp(ATTR_PS_START_DATE, this.startDate);
        map.putTimestamp(ATTR_PS_CREATED_AT, this.createdAt);
        map.put(ATTR_PS_CREATED_BY, this.createdBy);

        return map;
    }
}
