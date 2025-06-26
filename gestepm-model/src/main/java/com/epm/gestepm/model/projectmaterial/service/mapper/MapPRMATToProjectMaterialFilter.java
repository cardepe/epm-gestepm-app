package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.filter.ProjectMaterialFilter;
import com.epm.gestepm.modelapi.projectmaterial.dto.filter.ProjectMaterialFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialFilter {

  ProjectMaterialFilter from(ProjectMaterialFilterDto filterDto);

}
