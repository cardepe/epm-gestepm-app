package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.deleter.ProgrammedShareDelete;
import com.epm.gestepm.modelapi.shares.programmed.dto.deleter.ProgrammedShareDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareDelete {

  ProgrammedShareDelete from(ProgrammedShareDeleteDto deleteDto);

}
