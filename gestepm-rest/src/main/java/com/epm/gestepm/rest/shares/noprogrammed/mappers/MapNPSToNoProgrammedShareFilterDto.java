package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareFilterDto {

  NoProgrammedShareFilterDto from(NoProgrammedShareListRestRequest req);

}
