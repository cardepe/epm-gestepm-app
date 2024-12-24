package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.rest.inspection.request.InspectionListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionFilterDto {

  InspectionFilterDto from(InspectionListRestRequest req);

}
