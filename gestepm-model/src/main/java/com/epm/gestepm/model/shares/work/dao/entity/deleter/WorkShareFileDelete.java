package com.epm.gestepm.model.shares.work.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareFileAttributes.ATTR_WSF_ID;

@Data
public class WorkShareFileDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_WSF_ID, this.id);

        return map;
    }

}
