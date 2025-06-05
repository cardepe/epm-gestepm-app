package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.modelapi.user.dto.filter.UserFilterDto;
import com.epm.gestepm.rest.user.request.UserListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserFilterDto {

  // todo password to md5
  UserFilterDto from(UserListRestRequest req);

}
