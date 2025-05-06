package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareByIdFinder;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareByIdFinder {

  ProgrammedShareByIdFinder from(ProgrammedShareByIdFinderDto finderDto);

}
