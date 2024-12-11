package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.filter.InspectionFilter;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionFilter {

  InspectionFilter from(InspectionFilterDto filterDto);

}
