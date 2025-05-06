package com.epm.gestepm.model.shares.programmed.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.ATTR_PSF_ID;

@Data
public class ProgrammedShareFileDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PSF_ID, this.id);

        return map;
    }

}
