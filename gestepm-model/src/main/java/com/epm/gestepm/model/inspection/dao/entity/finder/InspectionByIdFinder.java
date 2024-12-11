package com.epm.gestepm.model.inspection.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes.ATTR_I_ID;

@Data
public class InspectionByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_I_ID, this.id);

    return map;
  }

}
