package com.epm.gestepm.model.personalexpensesheet.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.ATTR_PES_DESCRIPTION;
import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.ATTR_PES_PROJECT_ID;

@Data
public class PersonalExpenseSheetCreate implements CollectableAttributes {

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);

        return map;
    }

}
