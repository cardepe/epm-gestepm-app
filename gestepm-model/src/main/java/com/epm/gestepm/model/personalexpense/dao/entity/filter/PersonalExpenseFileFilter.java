package com.epm.gestepm.model.personalexpense.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseFileAttributes.ATTR_PEF_IDS;
import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseFileAttributes.ATTR_PEF_PE_ID;

@Data
public class PersonalExpenseFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer personalExpenseId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_PEF_IDS, this.ids);
        map.put(ATTR_PEF_PE_ID, this.personalExpenseId);

        return map;
    }
}
