package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.CreateNoProgrammedShareV1Request;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareCreateDto {

  NoProgrammedShareCreateDto from(CreateNoProgrammedShareV1Request req);

}
