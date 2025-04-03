package com.epm.gestepm.model.teleworkingsigning.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.teleworkingsigning.dao.constants.TeleworkingSigningAttributes.ATTR_TS_ID;

@Data
public class TeleworkingSigningByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_TS_ID, this.id);

    return map;
  }
}
