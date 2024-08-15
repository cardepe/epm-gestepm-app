package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.restapi.openapi.model.ReqUpdateNoProgrammedShare;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareUpdateDto {

  NoProgrammedShareUpdateDto from(ReqUpdateNoProgrammedShare req);

}
