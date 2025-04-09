package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.creator.ConstructionShareCreate;
import com.epm.gestepm.modelapi.shares.construction.dto.creator.ConstructionShareCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareCreate {

  ConstructionShareCreate from(ConstructionShareCreateDto createDto);

}
