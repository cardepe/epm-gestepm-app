package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.updater.ProjectUpdate;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectUpdateDto {

  ProjectUpdateDto from(ProjectUpdate updateDto);

  ProjectUpdateDto from(ProjectDto dto);

}
