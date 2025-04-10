package com.epm.gestepm.model.shares.displacement.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareAttributes.ATTR_DI_ID;

@Data
public class DisplacementShareDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_DI_ID, this.id);

        return map;
    }

}
