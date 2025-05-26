package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.updater.UserUpdate;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserUpdateDto {

  UserUpdateDto from(UserUpdate updateDto);

  UserUpdateDto from(UserDto dto);

}
