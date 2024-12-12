package com.epm.gestepm.model.inspection.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes.ATTR_IF_ID;

@Data
public class InspectionFileByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_IF_ID, this.id);

    return map;
  }

}
