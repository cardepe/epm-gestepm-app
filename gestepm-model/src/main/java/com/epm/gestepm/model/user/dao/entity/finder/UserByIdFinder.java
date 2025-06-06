package com.epm.gestepm.model.user.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareAttributes.ATTR_CS_ID;
import static com.epm.gestepm.model.user.dao.constants.UserAttributes.ATTR_U_ID;

@Data
public class UserByIdFinder implements CollectableAttributes {

  @NotNull
  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_U_ID, this.id);

    return map;
  }

}
