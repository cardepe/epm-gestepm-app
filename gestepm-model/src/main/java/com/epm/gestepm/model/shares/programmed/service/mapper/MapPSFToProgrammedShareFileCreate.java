package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareFileCreate;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSFToProgrammedShareFileCreate {

  ProgrammedShareFileCreate from(ProgrammedShareFileCreateDto createDto);

}
