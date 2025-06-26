package com.epm.gestepm.model.project.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.model.project.dao.entity.ProjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private String nameContains;

    private List<ProjectType> types;

    private List<Integer> activityCenterIds;

    private Integer state;

    private List<Integer> responsibleIds;

    private List<Integer> memberIds;

    private List<Integer> projectLeaderIds;

    private Boolean role;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.putList(ATTR_PR_IDS, this.ids);
        map.putLike(ATTR_PR_NAME_CONTAINS, this.nameContains);
        map.putEnumList(ATTR_PR_TYPES, this.types);
        map.put(ATTR_PR_ACTIVITY_CENTER_IDS, this.activityCenterIds);
        map.put(ATTR_PR_STATE, this.getState());
        map.putList(ATTR_PR_RESPONSIBLE_IDS, this.responsibleIds);
        map.putList(ATTR_PR_MEMBER_IDS, this.memberIds);
        map.putList(ATTR_PR_PROJECT_LEADER_IDS, this.projectLeaderIds);

        return map;
    }

    public Integer getState() {
        return state != null ? state : 1;
    }
}
