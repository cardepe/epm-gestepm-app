package com.epm.gestepm.model.constructionshare.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

import static com.epm.gestepm.model.constructionshare.dao.constants.ConstructionShareAttributes.*;

@Data
@Builder
public class ConstructionShareFilter implements CollectableAttributes {

    private List<Integer> ids;

    private Integer userId;

    private Integer projectId;

    private Integer userSigningId;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private Integer progress;

    private Integer activityCenterId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_CS_IDS, this.ids);
        map.put(ATTR_CS_U_ID, this.userId);
        map.put(ATTR_CS_P_ID, this.projectId);
        map.put(ATTR_CS_US_ID, this.userSigningId);
        map.putTimestamp(ATTR_CS_START_DATE, this.startDate);
        map.putTimestamp(ATTR_CS_END_DATE, this.endDate);
        map.put(ATTR_CS_PROGRESS, this.progress);
        map.put(ATTR_CS_AC_ID, this.activityCenterId);

        return map;
    }
}
