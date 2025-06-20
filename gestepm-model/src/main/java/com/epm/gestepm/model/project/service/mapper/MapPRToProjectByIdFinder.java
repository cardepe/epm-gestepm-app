package com.epm.gestepm.model.project.service.mapper;

import com.epm.gestepm.model.project.dao.entity.finder.ProjectByIdFinder;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPRToProjectByIdFinder {

  ProjectByIdFinder from(ProjectByIdFinderDto finderDto);

}
