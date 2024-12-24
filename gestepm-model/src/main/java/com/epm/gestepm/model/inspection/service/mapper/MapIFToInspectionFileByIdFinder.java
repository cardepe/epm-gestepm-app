package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.finder.InspectionFileByIdFinder;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToInspectionFileByIdFinder {

  InspectionFileByIdFinder from(InspectionFileByIdFinderDto finderDto);

}
