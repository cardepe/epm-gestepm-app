package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateInspectionV1RequestFilesInner;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1RequestFilesInner;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface MapIFToFileDto {

    InspectionFileCreateDto from(UpdateInspectionV1RequestFilesInner req);

    List<InspectionFileCreateDto> from(List<UpdateNoProgrammedShareV1RequestFilesInner> list);

}
