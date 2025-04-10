package com.epm.gestepm.model.shares.displacement.service.mapper;

import com.epm.gestepm.model.shares.displacement.dao.entity.filter.DisplacementShareFilter;
import com.epm.gestepm.modelapi.shares.displacement.dto.filter.DisplacementShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapDSToDisplacementShareFilter {

    DisplacementShareFilter from(DisplacementShareFilterDto filterDto);

}
