package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.model.projectmaterial.dao.entity.deleter.ProjectMaterialDelete;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRMATToProjectMaterialDelete {

  ProjectMaterialDelete from(ProjectMaterialDeleteDto deleteDto);

}
