package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFileFilter;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSFToWorkShareFileFilter {

  WorkShareFileFilter from(WorkShareFileFilterDto filterDto);

}
