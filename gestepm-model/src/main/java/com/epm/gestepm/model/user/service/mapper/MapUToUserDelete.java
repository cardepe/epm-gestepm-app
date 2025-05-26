package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.deleter.UserDelete;
import com.epm.gestepm.modelapi.user.dto.deleter.UserDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserDelete {

  UserDelete from(UserDeleteDto deleteDto);

}
