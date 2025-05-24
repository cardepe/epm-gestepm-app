package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.creator.UserCreate;
import com.epm.gestepm.modelapi.user.dto.creator.UserCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserCreate {

  UserCreate from(UserCreateDto createDto);

}
