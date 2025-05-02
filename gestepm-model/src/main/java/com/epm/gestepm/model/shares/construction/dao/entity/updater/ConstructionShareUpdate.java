package com.epm.gestepm.model.shares.construction.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditClose;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareAttributes.*;

@Data
public class ConstructionShareUpdate implements AuditClose, CollectableAttributes {

    @NotNull
    private Integer id;

    private Integer projectId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String operatorSignature;

    private LocalDateTime closedAt;

    private Integer closedBy;
    
    @Singular
    private Set<ConstructionShareFileCreate> files;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CS_ID, this.id);
        map.put(ATTR_CS_P_ID, this.projectId);
        map.putTimestamp(ATTR_CS_START_DATE, this.startDate);
        map.putTimestamp(ATTR_CS_END_DATE, this.endDate);
        map.put(ATTR_CS_OBSERVATIONS, this.observations);
        map.put(ATTR_CS_OPERATOR_SIGNATURE, this.operatorSignature);
        map.putTimestamp(ATTR_CS_CLOSED_AT, this.closedAt);
        map.put(ATTR_CS_CLOSED_BY, this.closedBy);

        return map;
    }

}
