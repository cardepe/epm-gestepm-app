package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.entity.ActionEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes.*;

@Data
public class InspectionCreate implements CollectableAttributes {

    @NotNull
    private Integer userSigningId;

    @NotNull
    private Integer shareId;

    @NotNull
    private ActionEnum action;

    @NotNull
    private OffsetDateTime startDate;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_I_USER_SIGNING_ID, this.userSigningId);
        map.put(ATTR_I_SHARE_ID, this.shareId);
        map.put(ATTR_I_ACTION, this.action.getId());
        map.put(ATTR_I_START_DATE, this.startDate);
        map.put(ATTR_I_FIRST_TECHNICAL_ID, this.firstTechnicalId);
        map.put(ATTR_I_SECOND_TECHNICAL_ID, this.secondTechnicalId);

        return map;
    }
}
