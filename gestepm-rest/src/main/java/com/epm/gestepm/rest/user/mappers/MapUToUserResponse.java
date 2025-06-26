package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.restapi.openapi.model.User;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MapUToUserResponse {

  @Mapping(source = "activityCenterId", target = "activityCenter.id")
  @Mapping(source = "roleId", target = "role.id")
  @Mapping(source = "levelId", target = "level.id")
  @Mapping(source = ".", target = "fullName", qualifiedByName = "mapFullName")
  User from(UserDto dto);

  List<User> from(Page<UserDto> list);

  List<User> from(List<UserDto> list);

  @Named("mapFullName")
  static String mapFullName(UserDto user) {
    final StringBuilder builder = new StringBuilder();

    builder.append(user.getName());

    if (StringUtils.isNoneBlank(user.getSurnames())) {
      builder.append(" ").append(user.getSurnames());
    }

    return builder.toString();
  }
}
