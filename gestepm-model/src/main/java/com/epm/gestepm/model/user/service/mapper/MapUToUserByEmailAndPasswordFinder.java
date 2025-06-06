package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.model.user.dao.entity.finder.UserByEmailAndPasswordFinder;
import com.epm.gestepm.model.user.dao.entity.finder.UserByIdFinder;
import com.epm.gestepm.modelapi.user.dto.finder.UserByEmailAndPasswordFinderDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserByEmailAndPasswordFinder {

  UserByEmailAndPasswordFinder from(UserByEmailAndPasswordFinderDto finderDto);

}
