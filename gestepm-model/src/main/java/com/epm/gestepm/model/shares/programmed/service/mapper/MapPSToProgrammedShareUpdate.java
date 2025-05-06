package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.updater.ProgrammedShareUpdate;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.updater.ProgrammedShareUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapPSToProgrammedShareUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ProgrammedShareUpdate from(ProgrammedShareUpdateDto dto1, @MappingTarget ProgrammedShareUpdate dto2);

  ProgrammedShareUpdate from(ProgrammedShareUpdateDto updateDto);

  ProgrammedShareUpdate from(ProgrammedShareDto dto);

}
