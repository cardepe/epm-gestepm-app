package com.epm.gestepm.model.project.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.project.dao.entity.ProjectType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

import static com.epm.gestepm.model.project.dao.constants.ProjectAttributes.*;

@Data
public class ProjectUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private ProjectType type;

    @NotNull
    private Double objectiveCost;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer activityCenterId;

    private Integer forumId;

    @NotNull
    private Integer state;

    @NotNull
    private Set<Integer> responsibleIds;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PR_ID, this.id);
        map.put(ATTR_PR_NAME, this.name);
        map.putEnum(ATTR_PR_TYPE, this.type);
        map.put(ATTR_PR_OBJECTIVE_COST, this.objectiveCost);
        map.put(ATTR_PR_START_DATE, this.startDate);
        map.put(ATTR_PR_END_DATE, this.endDate);
        map.put(ATTR_PR_ACTIVITY_CENTER_ID, this.activityCenterId);
        map.put(ATTR_PR_FORUM_ID, this.forumId);
        map.put(ATTR_PR_STATE, this.state);

        return map;
    }

}
