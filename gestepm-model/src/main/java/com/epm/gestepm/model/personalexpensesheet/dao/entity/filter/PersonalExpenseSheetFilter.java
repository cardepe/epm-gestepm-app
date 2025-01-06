package com.epm.gestepm.model.personalexpensesheet.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseSheetFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private Integer projectId;

  private Integer userId;

  private String description;

  private LocalDateTime startDate;

  private PersonalExpenseSheetStatusEnumDto status;

  private String observations;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_PES_IDS, this.ids);
    map.put(ATTR_PES_PROJECT_ID, this.projectId);
    map.put(ATTR_PES_USER_ID, this.userId);
    map.putLike(ATTR_PES_DESCRIPTION, this.description);
    map.putTimestamp(ATTR_PES_START_DATE, this.startDate);
    map.putEnum(ATTR_PES_STATUS, this.status);
    map.putLike(ATTR_PES_OBSERVATIONS, this.observations);

    return map;
  }

}
