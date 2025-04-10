package com.epm.gestepm.model.shares.displacement.mapper;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapDSToShareTableDto {

    @Mapping(target = "shareType", constant = "ds")
    @Mapping(source = "projectName", target = "projectId")
    ShareTableDTO from(DisplacementShareDto displacementShare);

    List<ShareTableDTO> from(List<DisplacementShareDto> inspections);

}
