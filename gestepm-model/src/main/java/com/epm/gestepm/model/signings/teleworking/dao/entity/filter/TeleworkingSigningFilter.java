package com.epm.gestepm.model.signings.teleworking.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.signings.teleworking.dao.constants.TeleworkingSigningAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeleworkingSigningFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private List<Integer> userIds;

  private List<Integer> projectIds;

  private Boolean current;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_TS_IDS, this.ids);
    map.putList(ATTR_TS_USER_ID, this.userIds);
    map.putList(ATTR_TS_PROJECT_ID, this.projectIds);
    map.put(ATTR_TS_CURRENT, this.current);

    return map;
  }

}
