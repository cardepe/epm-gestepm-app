package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFileFilter;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSFToConstructionShareFileFilter {

  ConstructionShareFileFilter from(ConstructionShareFileFilterDto filterDto);

}
