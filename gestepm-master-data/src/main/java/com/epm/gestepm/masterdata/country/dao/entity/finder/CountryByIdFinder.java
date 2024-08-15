package com.epm.gestepm.masterdata.country.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.masterdata.country.dao.constants.CountryAttributes.ATTR_C_ID;

@Data
public class CountryByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_C_ID, this.id);

    return map;
  }

}
