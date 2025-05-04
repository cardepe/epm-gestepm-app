package com.epm.gestepm.model.shares.work.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareAttributes.ATTR_WS_ID;

@Data
public class WorkShareDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_WS_ID, this.id);

        return map;
    }

}
