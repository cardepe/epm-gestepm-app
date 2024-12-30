package com.epm.gestepm.model.personalexpensesheet.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
public class PersonalExpenseSheetUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_ID, this.id);
        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);

        return map;
    }

}
