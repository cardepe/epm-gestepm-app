package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.creator.ActivityCenterCreate;
import com.epm.gestepm.masterdata.api.activitycenter.dto.creator.ActivityCenterCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterCreate {

  ActivityCenterCreate from(ActivityCenterCreateDto createDto);

}
