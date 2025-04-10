package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.updater.DisplacementShareUpdate;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.updater.DisplacementShareUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapDSToDisplacementShareUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  DisplacementShareUpdate from(DisplacementShareUpdateDto dto1, @MappingTarget DisplacementShareUpdate dto2);

  DisplacementShareUpdate from(DisplacementShareUpdateDto updateDto);

  DisplacementShareUpdate from(DisplacementShareDto dto);

}
