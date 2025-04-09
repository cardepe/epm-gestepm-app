package com.epm.gestepm.model.shares.construction.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.ATTR_CSF_ID;

@Data
public class ConstructionShareFileByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_CSF_ID, this.id);

    return map;
  }

}
