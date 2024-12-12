package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFileFilter;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSFToNoProgrammedShareFileFilter {

  NoProgrammedShareFileFilter from(NoProgrammedShareFileFilterDto filterDto);

}
