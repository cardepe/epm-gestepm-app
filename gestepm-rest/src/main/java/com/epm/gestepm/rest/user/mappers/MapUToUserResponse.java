package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.restapi.openapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapUToUserResponse {

  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  @Mapping(source = "roleId", target = "role.id")
  @Mapping(source = "levelId", target = "level.id")
  User from(UserDto dto);

  List<User> from(Page<UserDto> list);

}
