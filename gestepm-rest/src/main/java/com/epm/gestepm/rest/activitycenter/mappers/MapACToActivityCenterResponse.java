package com.epm.gestepm.rest.activitycenter.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapACToActivityCenterResponse {

  @Mapping(source = "countryId", target = "country.id")
  ActivityCenter from(ActivityCenterDto dto);

  List<ActivityCenter> from(Page<ActivityCenterDto> list);

}
