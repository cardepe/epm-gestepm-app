package com.epm.gestepm.masterdata.displacement.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.masterdata.displacement.dao.entity.DisplacementType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.masterdata.displacement.dao.constants.DisplacementAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class DisplacementFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    final List<Integer> activityCenterIds;

    private String name;

    private DisplacementType type;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_D_IDS, this.ids);
        map.put(ATTR_D_AC_IDS, this.activityCenterIds);
        map.putLike(ATTR_D_NAME, this.name);
        map.putEnum(ATTR_D_TYPE, this.type);

        return map;
    }
}
