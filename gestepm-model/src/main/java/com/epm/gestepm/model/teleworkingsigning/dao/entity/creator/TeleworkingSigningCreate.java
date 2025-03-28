package com.epm.gestepm.model.teleworkingsigning.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.teleworkingsigning.dao.constants.TeleworkingSigningAttributes.*;

@Data
public class TeleworkingSigningCreate implements AuditCreate, CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private LocalDateTime startedAt;
    
    private String startedLocation;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_TS_USER_ID, this.userId);
        map.put(ATTR_TS_PROJECT_ID, this.projectId);
        map.putTimestamp(ATTR_TS_STARTED_AT, this.startedAt);
        map.put(ATTR_TS_STARTED_LOCATION, this.startedLocation);

        return map;
    }

}
