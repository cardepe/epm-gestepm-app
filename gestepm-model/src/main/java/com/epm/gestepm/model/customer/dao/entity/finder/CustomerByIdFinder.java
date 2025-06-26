package com.epm.gestepm.model.customer.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.customer.dao.constants.CustomerAttributes.ATTR_CU_ID;
import static com.epm.gestepm.model.customer.dao.constants.CustomerAttributes.ATTR_CU_P_ID;

@Data
public class CustomerByIdFinder implements CollectableAttributes {

  @NotNull
  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_CU_ID, this.id);

    return map;
  }

}
