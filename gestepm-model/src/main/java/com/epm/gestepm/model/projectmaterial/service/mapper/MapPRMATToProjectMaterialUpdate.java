package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.updater.ProjectMaterialUpdate;
import com.epm.gestepm.modelapi.projectmaterial.dto.updater.ProjectMaterialUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialUpdate {

  ProjectMaterialUpdate from(ProjectMaterialUpdateDto updateDto);

}
