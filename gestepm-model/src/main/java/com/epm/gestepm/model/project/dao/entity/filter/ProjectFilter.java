package com.epm.gestepm.model.project.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.*;
import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.ATTR_PR_IS_TELEWORKING;
import static com.epm.gestepm.model.user.dao.constants.UserAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private String nameContains;

    private Boolean isStation;

    private List<Integer> activityCenterIds;

    private Boolean isTeleworking;

    private Integer state;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_PR_IDS, this.ids);
        map.put(ATTR_PR_NAME_CONTAINS, this.nameContains);
        map.putBooleanAsInt(ATTR_PR_IS_STATION, this.isStation);
        map.put(ATTR_PR_ACTIVITY_CENTER_IDS, this.activityCenterIds);
        map.putBooleanAsInt(ATTR_PR_IS_TELEWORKING, this.isTeleworking);
        map.put(ATTR_PR_STATE, this.state);

        return map;
    }

    public Integer getState() {
        return state != null ? state : 1;
    }
}
