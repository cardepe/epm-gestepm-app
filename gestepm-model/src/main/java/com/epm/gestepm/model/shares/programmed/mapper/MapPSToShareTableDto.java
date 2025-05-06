package com.epm.gestepm.model.shares.programmed.mapper;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapPSToShareTableDto {

    @Mapping(target = "shareType", constant = "ps")
    @Mapping(source = "projectName", target = "projectId")
    ShareTableDTO from(ProgrammedShareDto programmedShare);

    List<ShareTableDTO> from(List<ProgrammedShareDto> programmedShares);

}
