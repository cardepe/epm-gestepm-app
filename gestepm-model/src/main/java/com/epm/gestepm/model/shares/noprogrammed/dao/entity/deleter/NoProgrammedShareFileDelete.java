package com.epm.gestepm.model.shares.noprogrammed.dao.entity.deleter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.ATTR_NPSF_ID;

@Data
public class NoProgrammedShareFileDelete implements CollectableAttributes {

    @NotNull
    private Integer id;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_NPSF_ID, this.id);

        return map;
    }

}
