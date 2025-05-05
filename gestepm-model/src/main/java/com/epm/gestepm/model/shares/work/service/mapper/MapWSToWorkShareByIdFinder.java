package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.finder.WorkShareByIdFinder;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareByIdFinderDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareByIdFinder {

  WorkShareByIdFinder from(WorkShareByIdFinderDto finderDto);

}
