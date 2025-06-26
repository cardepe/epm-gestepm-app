package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.deleter.ProjectLeaderDelete;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectLeaderDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRLToProjectLeaderDelete {

  ProjectLeaderDelete from(ProjectLeaderDeleteDto deleteDto);

}
