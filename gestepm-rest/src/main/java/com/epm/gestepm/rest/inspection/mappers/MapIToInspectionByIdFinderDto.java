package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.rest.inspection.request.InspectionFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionByIdFinderDto {

  InspectionByIdFinderDto from(InspectionFindRestRequest req);

}
