package com.epm.gestepm.model.teleworkingsigning.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditApprovePaidDischarge;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;
import static com.epm.gestepm.model.teleworkingsigning.dao.constants.TeleworkingSigningAttributes.*;

@Data
public class TeleworkingSigningUpdate implements AuditApprovePaidDischarge, CollectableAttributes {

    @NotNull
    private Integer id;

    private LocalDateTime startedAt;
    
    @NotNull
    private LocalDateTime closedAt;

    private String closedLocation;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_TS_ID, this.id);
        map.putTimestamp(ATTR_TS_STARTED_AT, this.startedAt);
        map.putTimestamp(ATTR_TS_CLOSED_AT, this.closedAt);
        map.put(ATTR_TS_CLOSED_LOCATION, this.closedLocation);

        return map;
    }

}
