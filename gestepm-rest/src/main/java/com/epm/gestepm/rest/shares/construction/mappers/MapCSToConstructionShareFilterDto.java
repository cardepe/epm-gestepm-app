package com.epm.gestepm.rest.shares.construction.mappers;

import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareFilterDto {

  ConstructionShareFilterDto from(ConstructionShareListRestRequest req);

}
