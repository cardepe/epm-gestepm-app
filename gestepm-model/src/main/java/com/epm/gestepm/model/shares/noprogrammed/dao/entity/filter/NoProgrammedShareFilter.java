package com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes.*;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareAttributes.*;
import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareAttributes.ATTR_PS_STATUS;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoProgrammedShareFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private List<Integer> userIds;

  private List<Integer> projectIds;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private ShareStatusDto status;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_NPS_IDS, this.ids);
    map.putList(ATTR_NPS_U_IDS, this.userIds);
    map.putList(ATTR_NPS_P_IDS, this.projectIds);
    map.putTimestamp(ATTR_NPS_START_DATE, this.startDate);
    map.putTimestamp(ATTR_NPS_END_DATE, this.endDate);
    map.putEnum(ATTR_NPS_STATUS, this.status);

    return map;
  }

}
