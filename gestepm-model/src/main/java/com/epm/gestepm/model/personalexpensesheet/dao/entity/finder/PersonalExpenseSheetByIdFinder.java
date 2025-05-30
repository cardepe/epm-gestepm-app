package com.epm.gestepm.model.personalexpensesheet.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.ATTR_PES_ID;

@Data
public class PersonalExpenseSheetByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_PES_ID, this.id);

    return map;
  }
}
