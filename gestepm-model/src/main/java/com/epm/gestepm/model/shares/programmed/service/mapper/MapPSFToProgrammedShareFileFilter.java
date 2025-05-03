package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFileFilter;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSFToProgrammedShareFileFilter {

  ProgrammedShareFileFilter from(ProgrammedShareFileFilterDto filterDto);

}
