package com.epm.gestepm.rest.shares.construction.mappers;

import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateConstructionShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareCreateDto {

  ConstructionShareCreateDto from(CreateConstructionShareV1Request req);

}
