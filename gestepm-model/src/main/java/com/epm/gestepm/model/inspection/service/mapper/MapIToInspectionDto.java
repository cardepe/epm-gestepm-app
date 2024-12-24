package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapIToInspectionDto {

  InspectionDto from(Inspection country);

  List<InspectionDto> from(List<Inspection> country);

  default Page<InspectionDto> from(Page<Inspection> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
