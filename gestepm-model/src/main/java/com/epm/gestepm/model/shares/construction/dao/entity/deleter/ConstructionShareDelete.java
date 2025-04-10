package com.epm.gestepm.model.shares.construction.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareAttributes.ATTR_CS_ID;

@Data
public class ConstructionShareDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CS_ID, this.id);

        return map;
    }

}
