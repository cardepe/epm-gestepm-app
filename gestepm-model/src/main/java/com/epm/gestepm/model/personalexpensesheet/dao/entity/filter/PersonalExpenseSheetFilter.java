package com.epm.gestepm.model.personalexpensesheet.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
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

  private String description;

  private PersonalExpenseSheetStatusEnum status;

  private String observations;

  private LocalDateTime createdAt;

  private Integer createdBy;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_PES_IDS, this.ids);
    map.put(ATTR_PES_PROJECT_ID, this.projectId);
    map.putLike(ATTR_PES_DESCRIPTION, this.description);
    map.putEnum(ATTR_PES_STATUS, this.status);
    map.putLike(ATTR_PES_OBSERVATIONS, this.observations);
    map.putTimestamp(ATTR_PES_CREATED_AT, this.createdAt);
    map.put(ATTR_PES_CREATED_BY, this.createdBy);

    return map;
  }

}
