package com.epm.gestepm.model.shares.displacement.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class DisplacementShareFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private List<Integer> userIds;

    private List<Integer> projectIds;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_DS_IDS, this.ids);
        map.put(ATTR_DS_U_IDS, this.userIds);
        map.put(ATTR_DS_P_IDS, this.projectIds);
        map.putTimestamp(ATTR_DS_START_DATE, this.startDate);
        map.putTimestamp(ATTR_DS_END_DATE, this.endDate);

        return map;
    }
}
