package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareFileByIdFinder;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareFileByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSFToWorkShareFileByIdFinder {

  WorkShareFileByIdFinder from(WorkShareFileByIdFinderDto finderDto);

}
