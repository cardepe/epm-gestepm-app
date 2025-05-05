package com.epm.gestepm.model.shares.work.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.work.dao.constants.WorkShareFileAttributes.ATTR_WSF_ID;

@Data
public class WorkShareFileByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_WSF_ID, this.id);

    return map;
  }

}
