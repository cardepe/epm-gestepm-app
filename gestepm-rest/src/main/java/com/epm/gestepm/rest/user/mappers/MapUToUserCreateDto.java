package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.modelapi.user.dto.creator.UserCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateUserV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserCreateDto {

  UserCreateDto from(CreateUserV1Request req);

}
