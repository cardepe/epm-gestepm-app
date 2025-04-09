package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.updater.ConstructionShareUpdate;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.updater.ConstructionShareUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapCSToConstructionShareUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ConstructionShareUpdate from(ConstructionShareUpdateDto dto1, @MappingTarget ConstructionShareUpdate dto2);

  ConstructionShareUpdate from(ConstructionShareUpdateDto updateDto);

  ConstructionShareUpdate from(ConstructionShareDto dto);

}
