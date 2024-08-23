package com.epm.gestepm.masterdata.activitycenter.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;

import java.util.List;

import static com.epm.gestepm.masterdata.activitycenter.dao.constants.ActivityCenterAttributes.*;

@Data
public class ActivityCenterFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private String name;

  private List<Integer> countryIds;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_AC_IDS, this.ids);
    map.putLike(ATTR_AC_NAME, this.name);
    map.put(ATTR_AC_COUNTRY_IDS, this.countryIds);

    return map;
  }

}
