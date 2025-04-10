package com.epm.gestepm.model.shares.displacement.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.displacement.dao.constants.DisplacementShareAttributes.ATTR_DI_ID;

@Data
public class DisplacementShareByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_DI_ID, this.id);

    return map;
  }

}
