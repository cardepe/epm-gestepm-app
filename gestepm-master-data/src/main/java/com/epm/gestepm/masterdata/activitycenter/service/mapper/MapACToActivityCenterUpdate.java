package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.updater.ActivityCenterUpdate;
import com.epm.gestepm.masterdata.api.activitycenter.dto.updater.ActivityCenterUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterUpdate {

  ActivityCenterUpdate from(ActivityCenterUpdateDto updateDto);

}
