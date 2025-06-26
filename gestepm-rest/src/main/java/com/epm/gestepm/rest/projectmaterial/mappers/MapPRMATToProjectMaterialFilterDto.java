package com.epm.gestepm.rest.projectmaterial.mappers;

import com.epm.gestepm.modelapi.projectmaterial.dto.filter.ProjectMaterialFilterDto;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialFilterDto {

  ProjectMaterialFilterDto from(ProjectMaterialListRestRequest req);

}
