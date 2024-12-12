package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.creator.InspectionFileCreate;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToInspectionFileCreate {

  InspectionFileCreate from(InspectionFileCreateDto createDto);

}
