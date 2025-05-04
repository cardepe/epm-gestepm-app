package com.epm.gestepm.rest.shares.programmed.mappers;

import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFilterDto;
import com.epm.gestepm.rest.shares.programmed.request.ProgrammedShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareFilterDto {

  ProgrammedShareFilterDto from(ProgrammedShareListRestRequest req);

}
