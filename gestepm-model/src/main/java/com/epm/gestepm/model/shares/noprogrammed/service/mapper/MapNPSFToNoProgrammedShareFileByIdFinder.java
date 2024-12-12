package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.finder.NoProgrammedShareFileByIdFinder;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSFToNoProgrammedShareFileByIdFinder {

  NoProgrammedShareFileByIdFinder from(NoProgrammedShareFileByIdFinderDto finderDto);

}
