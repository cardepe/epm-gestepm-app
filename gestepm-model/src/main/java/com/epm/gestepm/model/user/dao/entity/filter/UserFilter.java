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

    private String email;

    private String password;

    private List<Integer> activityCenterIds;

    private Integer state;

    private List<Integer> signingIds;

    private List<Integer> roleIds;

    private List<Integer> levelIds;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_U_IDS, this.ids);
        map.put(ATTR_U_EMAIL, this.email);
        map.put(ATTR_U_PASSWORD, this.password);
        map.putList(ATTR_U_ACTIVITY_CENTER_ID, this.activityCenterIds);
        map.put(ATTR_U_STATE, this.state);
        map.putList(ATTR_U_SIGNING_ID, this.signingIds);
        map.putList(ATTR_U_ROLE_ID, this.roleIds);
        map.putList(ATTR_U_LEVEL_ID, this.levelIds);

        return map;
    }
}
