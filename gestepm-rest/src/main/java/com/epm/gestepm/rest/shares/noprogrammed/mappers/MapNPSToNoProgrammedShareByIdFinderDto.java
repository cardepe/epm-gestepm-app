package com.epm.gestepm.rest.shares.noprogrammed.mappers;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareFindRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareByIdFinderDto {

  NoProgrammedShareByIdFinderDto from(NoProgrammedShareFindRestRequest req);

}
