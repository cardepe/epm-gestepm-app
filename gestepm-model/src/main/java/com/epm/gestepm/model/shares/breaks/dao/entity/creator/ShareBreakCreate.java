package com.epm.gestepm.model.shares.breaks.dao.entity.creator;

import com.epm.gestepm.lib.audit.AuditCreate;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakAttributes.*;

@Data
public class ShareBreakCreate implements AuditCreate, CollectableAttributes {

    private Integer constructionShareId;

    private Integer programmedShareId;

    private Integer inspectionId;

    private Integer workShareId;

    @NotNull
    private LocalDateTime startDate;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CSB_CS_ID, this.constructionShareId);
        map.put(ATTR_CSB_PS_ID, this.programmedShareId);
        map.put(ATTR_CSB_INS_ID, this.inspectionId);
        map.put(ATTR_CSB_WS_ID, this.workShareId);
        map.putTimestamp(ATTR_CSB_START_DATE, this.startDate);

        return map;
    }
}
