package com.epm.gestepm.rest.project.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MapPRToProjectResponse {

  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  @Mapping(source = "responsibleIds", target = "responsible", qualifiedByName = "mapResponsible")
  Project from(ProjectDto dto);

  List<Project> from(Page<ProjectDto> list);

  @Named("mapResponsible")
  static List<User> mapResponsible(final Set<Integer> responsibleIds) {
    return responsibleIds.stream()
            .map(id -> new User().id(id))
            .collect(Collectors.toList());
  }
}
