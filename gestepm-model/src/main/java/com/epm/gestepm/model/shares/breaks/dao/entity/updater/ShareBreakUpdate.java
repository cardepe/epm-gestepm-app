package com.epm.gestepm.model.shares.breaks.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditClose;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakAttributes.*;

@Data
public class ShareBreakUpdate implements AuditClose, CollectableAttributes {

    @NotNull
    private Integer id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_SB_ID, this.id);
        map.putTimestamp(ATTR_SB_START_DATE, this.startDate);
        map.putTimestamp(ATTR_SB_END_DATE, this.endDate);

        return map;
    }

}
