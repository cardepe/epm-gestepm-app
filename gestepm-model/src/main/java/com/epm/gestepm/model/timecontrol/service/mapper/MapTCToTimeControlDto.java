package com.epm.gestepm.model.timecontrol.service.mapper;

import com.epm.gestepm.model.timecontrol.dao.entity.TimeControl;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapTCToTimeControlDto {

  TimeControlDto from(TimeControl timeControl);

  List<TimeControlDto> from(List<TimeControl> timeControl);

}
