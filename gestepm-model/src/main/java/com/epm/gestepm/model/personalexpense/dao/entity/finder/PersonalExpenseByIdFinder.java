package com.epm.gestepm.model.personalexpense.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseAttributes.ATTR_PE_ID;

@Data
public class PersonalExpenseByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_PE_ID, this.id);

    return map;
  }
}
