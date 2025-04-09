package com.epm.gestepm.model.shares.construction.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_IDS;
import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_SHARE_ID;

@Data
public class ConstructionShareFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer shareId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_CSF_IDS, this.ids);
        map.put(ATTR_CSF_SHARE_ID, this.shareId);

        return map;
    }
}
