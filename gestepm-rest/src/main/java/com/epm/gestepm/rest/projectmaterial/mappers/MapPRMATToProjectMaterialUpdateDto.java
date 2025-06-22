package com.epm.gestepm.rest.projectmaterial.mappers;

import com.epm.gestepm.modelapi.projectmaterial.dto.updater.ProjectMaterialUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateProjectMaterialV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialUpdateDto {

  ProjectMaterialUpdateDto from(UpdateProjectMaterialV1Request req);

}
