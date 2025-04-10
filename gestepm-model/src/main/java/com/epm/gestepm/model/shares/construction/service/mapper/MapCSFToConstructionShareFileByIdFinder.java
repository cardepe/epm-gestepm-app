package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.finder.ConstructionShareFileByIdFinder;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSFToConstructionShareFileByIdFinder {

  ConstructionShareFileByIdFinder from(ConstructionShareFileByIdFinderDto finderDto);

}
