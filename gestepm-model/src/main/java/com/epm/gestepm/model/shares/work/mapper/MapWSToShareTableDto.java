package com.epm.gestepm.model.shares.work.mapper;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapWSToShareTableDto {

    @Mapping(target = "shareType", constant = "ws")
    @Mapping(source = "projectName", target = "projectId")
    ShareTableDTO from(WorkShareDto workShare);

    List<ShareTableDTO> from(List<WorkShareDto> workShares);

}
