package com.epm.gestepm.rest.shares.construction.mappers;

import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateConstructionShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareUpdateDto {

  ConstructionShareUpdateDto from(UpdateConstructionShareV1Request req);

}
