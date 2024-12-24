package com.epm.gestepm.model.inspection.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes.ATTR_I_IDS;
import static com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes.ATTR_I_SHARE_ID;

@Data
@EqualsAndHashCode(callSuper = true)
public class InspectionFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  @NotNull
  private Integer shareId;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_I_IDS, this.ids);
    map.put(ATTR_I_SHARE_ID, this.shareId);

    return map;
  }

}
