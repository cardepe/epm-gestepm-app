package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareCreate;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareCreate {

  WorkShareCreate from(WorkShareCreateDto createDto);

}
