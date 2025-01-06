package com.epm.gestepm.model.personalexpensesheet.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
public class PersonalExpenseSheetUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    private Integer projectId;

    private String description;

    private LocalDateTime startDate;

    private PersonalExpenseSheetStatusEnumDto status;

    private String observations;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_ID, this.id);
        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);
        map.putTimestamp(ATTR_PES_START_DATE, this.startDate);
        map.putEnum(ATTR_PES_STATUS, this.status);
        map.put(ATTR_PES_OBSERVATIONS, this.observations);

        return map;
    }

}
