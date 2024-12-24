package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateInspectionV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionCreateDto {

  InspectionCreateDto from(CreateInspectionV1Request req);

}
