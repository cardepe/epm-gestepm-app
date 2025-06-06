package com.epm.gestepm.model.user.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.user.dao.constants.UserAttributes.*;
import static com.epm.gestepm.model.user.dao.constants.UserAttributes.ATTR_U_WORKING_HOURS;

@Data
public class UserUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    private String name;

    private String surnames;

    private String email;

    private String password;

    private Integer activityCenterId;

    private Integer state;

    private Integer signingId;

    private String forumUsername;

    private String forumPassword;

    private Integer roleId;

    private Integer levelId;

    private Double workingHours;

    private Integer currentYearHolidaysCount;

    private Integer lastYearHolidaysCount;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_U_ID, this.id);
        map.put(ATTR_U_NAME, this.name);
        map.put(ATTR_U_SURNAMES, this.surnames);
        map.put(ATTR_U_EMAIL, this.email);
        map.put(ATTR_U_PASSWORD, this.password);
        map.put(ATTR_U_ACTIVITY_CENTER_ID, this.activityCenterId);
        map.put(ATTR_U_STATE, this.state);
        map.put(ATTR_U_SIGNING_ID, this.signingId);
        map.put(ATTR_U_FORUM_USERNAME, this.forumUsername);
        map.put(ATTR_U_FORUM_PASSWORD, this.forumPassword);
        map.put(ATTR_U_ROLE_ID, this.roleId);
        map.put(ATTR_U_LEVEL_ID, this.levelId);
        map.put(ATTR_U_WORKING_HOURS, this.workingHours);
        map.put(ATTR_U_CURRENT_YEAR_HOLIDAYS_COUNT, this.currentYearHolidaysCount);
        map.put(ATTR_U_LAST_YEAR_HOLIDAYS_COUNT, this.lastYearHolidaysCount);

        return map;
    }

}
