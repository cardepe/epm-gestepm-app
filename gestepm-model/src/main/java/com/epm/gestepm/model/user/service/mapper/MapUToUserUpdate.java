package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.updater.UserUpdate;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapUToUserUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  UserUpdate from(UserUpdateDto dto1, @MappingTarget UserUpdate dto2);

  UserUpdate from(UserUpdateDto updateDto);

  UserUpdate from(UserDto dto);

}
