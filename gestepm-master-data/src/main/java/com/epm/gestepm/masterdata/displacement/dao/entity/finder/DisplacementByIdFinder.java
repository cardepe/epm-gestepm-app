package com.epm.gestepm.masterdata.displacement.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementAttributes.ATTR_D_ID;

@Data
public class DisplacementByIdFinder implements CollectableAttributes {

    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_D_ID, this.id);

        return map;
    }
}
