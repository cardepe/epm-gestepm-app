package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionDelete;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIToInspectionDelete {

  InspectionDelete from(InspectionDeleteDto deleteDto);

}
