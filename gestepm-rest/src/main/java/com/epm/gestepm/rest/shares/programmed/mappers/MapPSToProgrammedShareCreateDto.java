package com.epm.gestepm.rest.shares.programmed.mappers;

import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateProgrammedShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareCreateDto {

  ProgrammedShareCreateDto from(CreateProgrammedShareV1Request req);

}
