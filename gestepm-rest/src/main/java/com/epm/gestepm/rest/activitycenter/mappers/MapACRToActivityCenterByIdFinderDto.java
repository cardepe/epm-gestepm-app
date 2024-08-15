package com.epm.gestepm.rest.activitycenter.mappers;

import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.rest.activitycenter.request.ActivityCenterFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapACRToActivityCenterByIdFinderDto {

  ActivityCenterByIdFinderDto from(ActivityCenterFindRestRequest req);

}
