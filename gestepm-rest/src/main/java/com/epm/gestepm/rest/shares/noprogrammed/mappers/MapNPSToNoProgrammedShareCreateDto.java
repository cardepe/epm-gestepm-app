package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.restapi.openapi.model.ReqCreateNoProgrammedShare;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareCreateDto {

  NoProgrammedShareCreateDto from(ReqCreateNoProgrammedShare req);

}
