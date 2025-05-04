package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.updater.WorkShareUpdate;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import com.epm.gestepm.modelapi.shares.work.dto.updater.WorkShareUpdateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareUpdateDto {

  WorkShareUpdateDto from(WorkShareUpdate updateDto);

  WorkShareUpdateDto from(WorkShareDto dto);

}
