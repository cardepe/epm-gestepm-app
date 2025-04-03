package com.epm.gestepm.model.timecontrol.service.mapper;

import com.epm.gestepm.model.timecontrol.dao.entity.filter.TimeControlFilter;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapTCToTimeControlFilter {

  TimeControlFilter from(TimeControlFilterDto filterDto);

}
