package com.epm.gestepm.model.customer.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.customer.dao.constants.CustomerAttributes.*;

@Data
public class CustomerCreate implements CollectableAttributes {

    @NotNull
    private String name;

    private String mainEmail;

    private String secondaryEmail;

    @NotNull
    private Integer projectId;

    @Override
    public AttributeMap collectAttributes() {
        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CU_NAME, this.name);
        map.put(ATTR_CU_MAIN_EMAIL, this.mainEmail);
        map.put(ATTR_CU_SECONDARY_EMAIL, this.secondaryEmail);
        map.put(ATTR_CU_P_ID, this.projectId);

        return map;
    }
}
