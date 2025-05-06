package com.epm.gestepm.model.shares.work.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareAttributes.ATTR_WS_ID;

@Data
public class WorkShareByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_WS_ID, this.id);

    return map;
  }

}
