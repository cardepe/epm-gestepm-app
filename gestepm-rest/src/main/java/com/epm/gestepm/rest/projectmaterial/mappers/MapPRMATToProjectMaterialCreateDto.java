package com.epm.gestepm.rest.projectmaterial.mappers;

import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProjectMaterialV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialCreateDto {

  ProjectMaterialCreateDto from(CreateProjectMaterialV1Request req);

}
