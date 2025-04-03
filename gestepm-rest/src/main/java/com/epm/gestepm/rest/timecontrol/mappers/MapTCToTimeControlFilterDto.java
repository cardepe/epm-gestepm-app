package com.epm.gestepm.rest.timecontrol.mappers;

import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.rest.timecontrol.request.TimeControlListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapTCToTimeControlFilterDto {

  TimeControlFilterDto from(TimeControlListRestRequest req);

}
