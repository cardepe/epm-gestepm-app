package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.updater.ShareBreakUpdate;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.updater.ShareBreakUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapSBToShareBreakUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ShareBreakUpdate from(ShareBreakUpdateDto dto1, @MappingTarget ShareBreakUpdate dto2);

  ShareBreakUpdate from(ShareBreakUpdateDto updateDto);

  ShareBreakUpdate from(ShareBreakDto dto);

}
