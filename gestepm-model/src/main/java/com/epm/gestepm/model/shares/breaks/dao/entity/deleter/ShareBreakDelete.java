package com.epm.gestepm.model.shares.breaks.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakAttributes.ATTR_SB_ID;

@Data
public class ShareBreakDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_SB_ID, this.id);

        return map;
    }

}
