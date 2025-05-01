package com.epm.gestepm.model.shares.construction.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareStatusDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConstructionShareFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private List<Integer> userIds;

    private List<Integer> projectIds;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private ConstructionShareStatusDto status;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_CS_IDS, this.ids);
        map.putList(ATTR_CS_U_IDS, this.userIds);
        map.putList(ATTR_CS_P_IDS, this.projectIds);
        map.putTimestamp(ATTR_CS_CREATED_AT, this.startDate);
        map.putTimestamp(ATTR_CS_CLOSED_AT, this.endDate);
        map.putEnum(ATTR_CS_STATUS, this.status);

        return map;
    }
}
