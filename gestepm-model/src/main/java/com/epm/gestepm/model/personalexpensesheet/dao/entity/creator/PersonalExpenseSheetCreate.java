package com.epm.gestepm.model.personalexpensesheet.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
public class PersonalExpenseSheetCreate implements CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private PersonalExpenseSheetStatusEnum status;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_USER_ID, this.userId);
        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);
        map.putTimestamp(ATTR_PES_START_DATE, this.startDate);
        map.putEnum(ATTR_PES_STATUS, this.status);

        return map;
    }

}
