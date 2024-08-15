package com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(NoProgrammedShareAttributes.ATTR_NPS_ID, this.id);

        return map;
    }

}
