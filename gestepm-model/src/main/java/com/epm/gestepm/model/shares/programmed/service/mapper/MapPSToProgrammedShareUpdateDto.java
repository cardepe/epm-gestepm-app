package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareUpdate;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareUpdateDto {

  ProgrammedShareUpdateDto from(ProgrammedShareUpdate updateDto);

  ProgrammedShareUpdateDto from(ProgrammedShareDto dto);

}
