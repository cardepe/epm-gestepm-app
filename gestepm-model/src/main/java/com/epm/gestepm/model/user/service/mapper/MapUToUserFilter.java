package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.filter.UserFilter;
import com.epm.gestepm.modelapi.user.dto.filter.UserFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserFilter {

    UserFilter from(UserFilterDto filterDto);

}
