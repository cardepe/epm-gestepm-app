package com.epm.gestepm.rest.shares.programmed.mappers;

import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareByIdFinderDto;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareByIdFinderDto {

  ProgrammedShareByIdFinderDto from(ProgrammedShareFindRestRequest req);

}
