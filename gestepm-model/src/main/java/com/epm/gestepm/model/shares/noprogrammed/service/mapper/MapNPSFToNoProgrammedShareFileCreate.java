package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator.NoProgrammedShareFileCreate;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSFToNoProgrammedShareFileCreate {

  NoProgrammedShareFileCreate from(NoProgrammedShareFileCreateDto createDto);

}
