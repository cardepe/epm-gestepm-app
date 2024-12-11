package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapIToInspectionUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  InspectionUpdate from(InspectionUpdateDto dto1, @MappingTarget InspectionUpdate dto2);

  InspectionUpdate from(InspectionUpdateDto updateDto);

  InspectionUpdate from(InspectionDto dto);

}
