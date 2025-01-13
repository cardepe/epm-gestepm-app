package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.deleter.InspectionFileDelete;
import com.epm.gestepm.modelapi.inspection.dto.deleter.InspectionFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapIFToInspectionFileDelete {

  InspectionFileDelete from(InspectionFileDeleteDto deleteDto);

}
