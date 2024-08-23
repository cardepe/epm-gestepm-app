package com.epm.gestepm.masterdata.country.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.masterdata.country.dao.constants.CountryAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class CountryFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private String name;

  private List<String> tags;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_C_IDS, this.ids);
    map.putLike(ATTR_C_NAME, this.name);
    map.putList(ATTR_C_TAGS, this.tags);

    return map;
  }

}
