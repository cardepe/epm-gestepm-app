package com.epm.gestepm.model.user.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.user.dao.constants.UserAttributes.*;

@Data
public class UserCreate implements CollectableAttributes {

    @NotNull
    private String name;

    @NotNull
    private String surnames;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String forumPassword;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private Integer state;

    @NotNull
    private Integer signingId;

    @NotNull
    private Integer roleId;

    private Integer levelId;

    @NotNull
    private Double workingHours;

    @NotNull
    private Integer currentYearHolidaysCount;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_U_NAME, this.name);
        map.put(ATTR_U_SURNAMES, this.surnames);
        map.put(ATTR_U_EMAIL, this.email);
        map.put(ATTR_U_PASSWORD, this.password);
        map.put(ATTR_U_FORUM_PASSWORD, this.forumPassword);
        map.put(ATTR_U_ACTIVITY_CENTER_ID, this.activityCenterId);
        map.put(ATTR_U_STATE, this.state);
        map.put(ATTR_U_SIGNING_ID, this.signingId);
        map.put(ATTR_U_ROLE_ID, this.roleId);
        map.put(ATTR_U_LEVEL_ID, this.levelId);
        map.put(ATTR_U_WORKING_HOURS, this.workingHours);
        map.put(ATTR_U_CURRENT_YEAR_HOLIDAYS_COUNT, this.currentYearHolidaysCount);

        return map;
    }
}
