package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.updater.ProjectUpdate;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapPRToProjectUpdate {

  ProjectUpdate from(ProjectUpdateDto updateDto);

}
