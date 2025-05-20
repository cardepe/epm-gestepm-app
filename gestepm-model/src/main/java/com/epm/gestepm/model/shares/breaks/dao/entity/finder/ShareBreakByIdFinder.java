package com.epm.gestepm.model.shares.breaks.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.breaks.dao.constants.ShareBreakAttributes.ATTR_SB_ID;

@Data
public class ShareBreakByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_SB_ID, this.id);

    return map;
  }

}
