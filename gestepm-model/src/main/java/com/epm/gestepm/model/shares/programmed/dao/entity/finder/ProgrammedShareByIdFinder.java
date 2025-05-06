package com.epm.gestepm.model.shares.programmed.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareAttributes.ATTR_PS_ID;

@Data
public class ProgrammedShareByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_PS_ID, this.id);

    return map;
  }

}
