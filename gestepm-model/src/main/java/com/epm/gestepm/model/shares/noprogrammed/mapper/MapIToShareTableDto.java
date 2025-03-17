package com.epm.gestepm.model.shares.noprogrammed.mapper;

import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapIToShareTableDto {

    @Mapping(target = "shareType", constant = "is")
    @Mapping(source = "projectName", target = "projectId")
    @Mapping(source = "firstTechnicalName", target = "username")
    ShareTableDTO from(InspectionDto inspection);

    List<ShareTableDTO> from(List<InspectionDto> inspections);

}
