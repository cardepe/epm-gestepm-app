package com.epm.gestepm.model.user.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareAttributes.*;
import static com.epm.gestepm.model.user.dao.constants.UserAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserFilter extends Orderable implements CollectableAttributes {

    private List<Integer> ids;

    private String nameContains;

    private String email;

    private String password;

    private List<Integer> activityCenterIds;

    private Integer state;

    private List<Integer> signingIds;

    private List<Integer> roleIds;

    private List<Integer> levelIds;

    private Integer leadingProjectId;

    private Integer memberProjectId;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_U_IDS, this.ids);
        map.putLike(ATTR_U_NAME_CONTAINS, this.nameContains);
        map.put(ATTR_U_EMAIL, this.email);
        map.put(ATTR_U_PASSWORD, this.password);
        map.putList(ATTR_U_ACTIVITY_CENTER_IDS, this.activityCenterIds);
        map.put(ATTR_U_STATE, this.getState());
        map.putList(ATTR_U_SIGNING_ID, this.signingIds);
        map.putList(ATTR_U_ROLE_IDS, this.roleIds);
        map.putList(ATTR_U_LEVEL_IDS, this.levelIds);
        map.put(ATTR_U_LEADING_PROJECT_ID, this.leadingProjectId);
        map.put(ATTR_U_MEMBER_PROJECT_ID, this.memberProjectId);

        return map;
    }

    public Integer getState() {
        return state != null ? state : 1;
    }
}
