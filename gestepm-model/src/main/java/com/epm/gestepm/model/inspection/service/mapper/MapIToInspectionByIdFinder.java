package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionByIdFinder;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionByIdFinder {

  InspectionByIdFinder from(InspectionByIdFinderDto finderDto);

}
