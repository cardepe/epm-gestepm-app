package com.epm.gestepm.rest.activitycenter.mappers;

import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.rest.activitycenter.request.ActivityCenterListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapACRToActivityCenterFilterDto {

  ActivityCenterFilterDto from(ActivityCenterListRestRequest req);

}
