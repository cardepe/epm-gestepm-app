package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFileFilter;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToInspectionFileFilter {

  InspectionFileFilter from(InspectionFileFilterDto filterDto);

}
