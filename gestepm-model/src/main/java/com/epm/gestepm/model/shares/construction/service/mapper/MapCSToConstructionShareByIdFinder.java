package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareByIdFinder;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareByIdFinder {

  ConstructionShareByIdFinder from(ConstructionShareByIdFinderDto finderDto);

}
