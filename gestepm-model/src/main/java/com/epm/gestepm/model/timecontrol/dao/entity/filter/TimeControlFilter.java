package com.epm.gestepm.model.timecontrol.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.model.timecontrol.dao.entity.TimeControlTypeEnum;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTypeEnumDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;
import static com.epm.gestepm.model.timecontrol.dao.constants.TimeControlAttributes.*;

@Data
public class TimeControlFilter implements CollectableAttributes {

  private Integer userId;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private List<TimeControlTypeEnumDto> types;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_TC_USER_ID, this.userId);
    map.putTimestamp(ATTR_TC_START_DATE, this.startDate);
    map.putTimestamp(ATTR_TC_END_DATE, this.endDate);
    map.putEnumList(ATTR_TC_TYPES, this.types);

    return map;
  }

}
