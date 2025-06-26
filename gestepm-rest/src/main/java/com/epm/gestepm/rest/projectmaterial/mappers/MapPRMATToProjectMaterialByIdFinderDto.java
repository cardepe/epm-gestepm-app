package com.epm.gestepm.rest.projectmaterial.mappers;

import com.epm.gestepm.modelapi.projectmaterial.dto.finder.ProjectMaterialByIdFinderDto;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialByIdFinderDto {

  ProjectMaterialByIdFinderDto from(ProjectMaterialFindRestRequest req);

}
