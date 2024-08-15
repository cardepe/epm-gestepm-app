package com.epm.gestepm.rest.activitycenter.mappers;

import com.epm.gestepm.masterdata.api.activitycenter.dto.creator.ActivityCenterCreateDto;
import com.epm.gestepm.restapi.openapi.model.ReqCreateActivityCenter;
import org.mapstruct.Mapper;

@Mapper
public interface MapACToActivityCenterCreateDto {

  ActivityCenterCreateDto from(ReqCreateActivityCenter req);

}
