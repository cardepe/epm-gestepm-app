package com.epm.gestepm.rest.timecontrol.mappers;

import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.restapi.openapi.model.TimeControl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapTCToTimeControlResponse {

  @Mapping(source = "userId", target = "user.id")
  TimeControl from(TimeControlDto dto);

  List<TimeControl> from(List<TimeControlDto> list);

}
