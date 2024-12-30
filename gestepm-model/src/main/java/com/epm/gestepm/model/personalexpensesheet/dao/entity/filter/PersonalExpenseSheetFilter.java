package com.epm.gestepm.model.personalexpensesheet.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseSheetFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private Integer projectId;

  private Integer userId;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_PES_IDS, this.ids);
    map.put(ATTR_PES_PROJECT_ID, this.projectId);
    map.put(ATTR_PES_USER_ID, this.userId);

    return map;
  }

}
