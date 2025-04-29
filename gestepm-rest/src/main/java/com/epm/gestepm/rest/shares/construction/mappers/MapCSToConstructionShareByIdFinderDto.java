package com.epm.gestepm.rest.shares.construction.mappers;

import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.rest.shares.construction.request.ConstructionShareFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareByIdFinderDto {

  ConstructionShareByIdFinderDto from(ConstructionShareFindRestRequest req);

}
