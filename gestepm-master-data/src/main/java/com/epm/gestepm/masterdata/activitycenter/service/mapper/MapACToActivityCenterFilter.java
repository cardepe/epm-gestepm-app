package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.filter.ActivityCenterFilter;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterFilter {

  ActivityCenterFilter from(ActivityCenterFilterDto filterDto);

}
