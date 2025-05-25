package com.epm.gestepm.rest.user.mappers;

import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.rest.user.request.UserFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapUToUserByIdFinderDto {

  UserByIdFinderDto from(UserFindRestRequest req);

}
