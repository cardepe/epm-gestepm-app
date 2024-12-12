package com.epm.gestepm.model.inspection.service.mapper;

import com.epm.gestepm.model.inspection.dao.entity.InspectionFile;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface MapIFToInspectionFileDto {

  @Mapping(source = ".", target = "name", qualifiedByName = "parseFileName")
  InspectionFileDto from(InspectionFile file);

  List<InspectionFileDto> from(List<InspectionFile> files);

  @Named("parseFileName")
  static String parseFileName(InspectionFile file) {
    return file.getName() + "." + file.getExt();
  }
}
