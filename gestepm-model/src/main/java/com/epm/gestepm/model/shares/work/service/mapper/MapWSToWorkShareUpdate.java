package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareUpdate;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface MapWSToWorkShareUpdate {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  WorkShareUpdate from(WorkShareUpdateDto dto1, @MappingTarget WorkShareUpdate dto2);

  WorkShareUpdate from(WorkShareUpdateDto updateDto);

  WorkShareUpdate from(WorkShareDto dto);

}
