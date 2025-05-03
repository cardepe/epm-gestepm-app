package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.creator.ProgrammedShareCreate;
import com.epm.gestepm.modelapi.shares.programmed.dto.creator.ProgrammedShareCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareCreate {

  ProgrammedShareCreate from(ProgrammedShareCreateDto createDto);

}
