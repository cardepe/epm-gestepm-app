package com.epm.gestepm.model.personalexpense.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseAttributes.ATTR_PE_IDS;
import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseAttributes.ATTR_PE_PES_ID;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private Integer personalExpenseSheetId;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_PE_IDS, this.ids);
    map.put(ATTR_PE_PES_ID, this.personalExpenseSheetId);

    return map;
  }

}
