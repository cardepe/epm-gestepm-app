package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionUpdateDto {

  InspectionUpdateDto from(InspectionUpdate updateDto);

}
