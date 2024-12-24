package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.UpdateNoProgrammedShareV1Request;
import org.mapstruct.Mapper;

@Mapper(uses = MapNPSFToFileDto.class)
public interface MapNPSToNoProgrammedShareUpdateDto {

  NoProgrammedShareUpdateDto from(UpdateNoProgrammedShareV1Request req);

}
