package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.deleter.WorkShareFileDelete;
import com.epm.gestepm.modelapi.shares.work.dto.deleter.WorkShareFileDeleteDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSFToWorkShareFileDelete {

  WorkShareFileDelete from(WorkShareFileDeleteDto deleteDto);

}
