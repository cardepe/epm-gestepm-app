package com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes.*;

@Data
public class NoProgrammedShareCreate implements CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private Integer userSigningId;

    @NotNull
    private OffsetDateTime startDate;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_NPS_U_ID, this.userId);
        map.put(ATTR_NPS_P_ID, this.projectId);
        map.put(ATTR_NPS_US_ID, this.userSigningId);
        map.put(ATTR_NPS_START_DATE, this.startDate);

        return map;
    }
}
