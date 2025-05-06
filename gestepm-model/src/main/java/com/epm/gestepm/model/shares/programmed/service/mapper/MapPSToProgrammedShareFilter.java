package com.epm.gestepm.model.shares.programmed.service.mapper;

import com.epm.gestepm.model.shares.programmed.dao.entity.filter.ProgrammedShareFilter;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapPSToProgrammedShareFilter {

    ProgrammedShareFilter from(ProgrammedShareFilterDto filterDto);

}
