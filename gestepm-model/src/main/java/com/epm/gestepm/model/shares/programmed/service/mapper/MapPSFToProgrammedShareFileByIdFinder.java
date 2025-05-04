package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.finder.ProgrammedShareFileByIdFinder;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSFToProgrammedShareFileByIdFinder {

  ProgrammedShareFileByIdFinder from(ProgrammedShareFileByIdFinderDto finderDto);

}
