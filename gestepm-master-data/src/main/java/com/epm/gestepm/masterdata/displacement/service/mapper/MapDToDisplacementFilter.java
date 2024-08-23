package com.epm.gestepm.masterdata.displacement.service.mapper;

import com.epm.gestepm.masterdata.api.displacement.dto.filter.DisplacementFilterDto;
import com.epm.gestepm.masterdata.displacement.dao.entity.filter.DisplacementFilter;
import org.mapstruct.Mapper;

@Mapper
public interface MapDToDisplacementFilter {

  DisplacementFilter from(DisplacementFilterDto filterDto);

}
