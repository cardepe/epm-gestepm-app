package com.epm.gestepm.rest.inspection.mappers;

import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface MapIFToFileResponse {

    ShareFile from(InspectionFileDto fileDto);

    List<ShareFile> from(List<InspectionFileDto> list);

}
