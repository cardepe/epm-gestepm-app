package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareFileCreate;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSFToConstructionShareFileCreate {

  ConstructionShareFileCreate from(ConstructionShareFileCreateDto createDto);

}
