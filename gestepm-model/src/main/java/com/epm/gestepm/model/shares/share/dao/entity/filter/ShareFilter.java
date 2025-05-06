package com.epm.gestepm.model.shares.share.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.shares.share.dao.constants.ShareAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShareFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private List<Integer> userIds;

    private List<Integer> projectIds;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private ShareStatusDto status;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_S_IDS, this.ids);
        map.putList(ATTR_S_U_IDS, this.userIds);
        map.putList(ATTR_S_P_IDS, this.projectIds);
        map.putTimestamp(ATTR_S_START_DATE, this.startDate);
        map.putTimestamp(ATTR_S_END_DATE, this.endDate);
        map.putEnum(ATTR_S_STATUS, this.status);

        return map;
    }
}
