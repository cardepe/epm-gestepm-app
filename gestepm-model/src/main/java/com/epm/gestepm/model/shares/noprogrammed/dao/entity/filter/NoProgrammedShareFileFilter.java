package com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.ATTR_NPSF_IDS;
import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes.ATTR_NPSF_SHARE_ID;

@Data
public class NoProgrammedShareFileFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer shareId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_NPSF_IDS, this.ids);
        map.put(ATTR_NPSF_SHARE_ID, this.shareId);

        return map;
    }
}
