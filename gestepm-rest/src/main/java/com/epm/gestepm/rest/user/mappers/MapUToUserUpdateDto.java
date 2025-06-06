package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateUserV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserUpdateDto {

  UserUpdateDto from(UpdateUserV1Request req);

}
