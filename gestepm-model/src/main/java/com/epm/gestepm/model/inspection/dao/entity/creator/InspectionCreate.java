package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.entity.ActionEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes.*;

@Data
public class InspectionCreate implements CollectableAttributes {

    private Integer userSigningId;

    @NotNull
    private Integer shareId;

    @NotNull
    private ActionEnum action;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_I_SHARE_ID, this.shareId);
        map.putEnum(ATTR_I_ACTION, this.action);
        map.put(ATTR_I_START_DATE, this.startDate);
        map.put(ATTR_I_FIRST_TECHNICAL_ID, this.firstTechnicalId);
        map.put(ATTR_I_SECOND_TECHNICAL_ID, this.secondTechnicalId);

        return map;
    }
}
