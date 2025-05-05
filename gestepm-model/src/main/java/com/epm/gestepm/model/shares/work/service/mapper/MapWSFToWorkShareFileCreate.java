package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.creator.WorkShareFileCreate;
import com.epm.gestepm.modelapi.shares.work.dto.creator.WorkShareFileCreateDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSFToWorkShareFileCreate {

  WorkShareFileCreate from(WorkShareFileCreateDto createDto);

}
