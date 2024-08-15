package com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes.ATTR_NPS_IDS;

@Data
public class NoProgrammedShareFilter implements CollectableAttributes {

  private List<Integer> ids;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_NPS_IDS, this.ids);

    return map;
  }

}
