package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.deleter.ActivityCenterDelete;
import com.epm.gestepm.masterdata.api.activitycenter.dto.deleter.ActivityCenterDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterDelete {

  ActivityCenterDelete from(ActivityCenterDeleteDto deleteDto);

}
