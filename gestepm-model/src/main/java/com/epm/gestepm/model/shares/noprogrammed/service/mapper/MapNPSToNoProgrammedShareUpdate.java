package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.NoProgrammedShareUpdate;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapNPSToNoProgrammedShareUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  NoProgrammedShareUpdate from(NoProgrammedShareUpdateDto dto1, @MappingTarget NoProgrammedShareUpdate dto2);

  NoProgrammedShareUpdate from(NoProgrammedShareUpdateDto updateDto);

  NoProgrammedShareUpdate from(NoProgrammedShareDto dto);

}
