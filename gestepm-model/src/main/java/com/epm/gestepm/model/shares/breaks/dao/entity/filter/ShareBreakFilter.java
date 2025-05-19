package com.epm.gestepm.model.shares.breaks.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShareBreakFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private List<Integer> constructionShareIds;

    private List<Integer> programmedShareIds;

    private List<Integer> inspectionIds;

    private List<Integer> workShareIds;

    private ShareStatusDto status;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_CSB_IDS, this.ids);
        map.putList(ATTR_CSB_CS_IDS, this.constructionShareIds);
        map.putList(ATTR_CSB_PS_IDS, this.programmedShareIds);
        map.putList(ATTR_CSB_INS_IDS, this.inspectionIds);
        map.putList(ATTR_CSB_WS_IDS, this.workShareIds);
        map.putEnum(ATTR_CSB_STATUS, this.status);

        return map;
    }
}
