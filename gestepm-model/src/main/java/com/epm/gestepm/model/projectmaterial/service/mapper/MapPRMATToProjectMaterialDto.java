package com.epm.gestepm.model.projectmaterial.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.projectmaterial.dao.entity.ProjectMaterial;
import com.epm.gestepm.modelapi.projectmaterial.dto.ProjectMaterialDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapPRMATToProjectMaterialDto {

  ProjectMaterialDto from(ProjectMaterial projectMaterial);

  List<ProjectMaterialDto> from(List<ProjectMaterial> projectMaterial);

  default Page<ProjectMaterialDto> from(Page<ProjectMaterial> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
