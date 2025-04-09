package com.epm.gestepm.model.shares.construction.service.mapper;

import com.epm.gestepm.model.shares.construction.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareFilter {

    ConstructionShareFilter from(ConstructionShareFilterDto filterDto);

}
