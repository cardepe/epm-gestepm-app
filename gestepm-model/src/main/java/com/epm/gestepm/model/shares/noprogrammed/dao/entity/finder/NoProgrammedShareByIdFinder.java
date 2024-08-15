package com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes.ATTR_NPS_ID;

@Data
public class NoProgrammedShareByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_NPS_ID, this.id);

    return map;
  }

}
