package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.finder.ProjectMaterialByIdFinder;
import com.epm.gestepm.modelapi.projectmaterial.dto.finder.ProjectMaterialByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialByIdFinder {

  ProjectMaterialByIdFinder from(ProjectMaterialByIdFinderDto finderDto);

}
