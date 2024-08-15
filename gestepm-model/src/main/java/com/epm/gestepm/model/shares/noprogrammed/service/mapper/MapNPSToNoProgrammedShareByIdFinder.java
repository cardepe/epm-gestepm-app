package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareByIdFinder;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareByIdFinder {

  NoProgrammedShareByIdFinder from(NoProgrammedShareByIdFinderDto finderDto);

}
