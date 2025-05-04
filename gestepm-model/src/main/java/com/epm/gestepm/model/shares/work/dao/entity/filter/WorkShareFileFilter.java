package com.epm.gestepm.model.shares.work.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareFileAttributes.ATTR_WSF_IDS;
import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareFileAttributes.ATTR_WSF_SHARE_ID;

@Data
public class WorkShareFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer shareId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_WSF_IDS, this.ids);
        map.put(ATTR_WSF_SHARE_ID, this.shareId);

        return map;
    }
}
