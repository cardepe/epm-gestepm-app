package com.epm.gestepm.model.shares.noprogrammed.service.mapper;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.filter.NoProgrammedShareFilter;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.filter.NoProgrammedShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapNPSToNoProgrammedShareFilter {

  NoProgrammedShareFilter from(NoProgrammedShareFilterDto filterDto);

}
