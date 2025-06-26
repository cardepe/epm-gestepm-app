package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.deleter.ProjectDelete;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectDelete {

  ProjectDelete from(ProjectDeleteDto deleteDto);

}
