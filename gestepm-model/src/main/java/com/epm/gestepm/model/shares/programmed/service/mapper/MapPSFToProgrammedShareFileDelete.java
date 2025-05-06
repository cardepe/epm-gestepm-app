package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareFileDelete;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSFToProgrammedShareFileDelete {

  ProgrammedShareFileDelete from(ProgrammedShareFileDeleteDto deleteDto);

}
