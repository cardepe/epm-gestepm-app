package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.masterdata.activitycenter.dao.entity.finder.ActivityCenterByIdFinder;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterByIdFinder {

  ActivityCenterByIdFinder from(ActivityCenterByIdFinderDto finderDto);

}
