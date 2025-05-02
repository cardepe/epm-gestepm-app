package com.epm.gestepm.model.shares.construction.mapper;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapCSToShareTableDto {

    @Mapping(target = "shareType", constant = "cs")
    @Mapping(source = "projectName", target = "projectId")
    ShareTableDTO from(ConstructionShareDto constructionShare);

    List<ShareTableDTO> from(List<ConstructionShareDto> constructionShares);

}
