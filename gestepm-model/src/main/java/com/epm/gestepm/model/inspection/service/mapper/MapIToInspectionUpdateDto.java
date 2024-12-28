package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.updater.InspectionUpdate;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapIToInspectionUpdateDto {

  @Mapping(source = "materialsFile", target = "materialsFile.content")
  @Mapping(source = "materialsFileExtension", target = "materialsFile.ext")
  InspectionUpdateDto from(InspectionUpdate updateDto);

  @Mapping(source = "materialsFile", target = "materialsFile.content")
  @Mapping(source = "materialsFileExtension", target = "materialsFile.ext")
  InspectionUpdateDto from(InspectionDto dto);

}
