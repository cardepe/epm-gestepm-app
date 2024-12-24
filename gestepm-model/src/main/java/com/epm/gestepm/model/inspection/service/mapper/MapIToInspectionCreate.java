package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionCreate;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionCreate {

  InspectionCreate from(InspectionCreateDto createDto);

}
