package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateInspectionV1Request;
import org.mapstruct.Mapper;

@Mapper(uses = MapIFToFileDto.class)
public interface MapIToInspectionUpdateDto {

  InspectionUpdateDto from(UpdateInspectionV1Request req);

}
