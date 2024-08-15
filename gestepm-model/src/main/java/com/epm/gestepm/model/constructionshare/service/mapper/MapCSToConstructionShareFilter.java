package com.epm.gestepm.model.constructionshare.service.mapper;

import com.epm.gestepm.model.constructionshare.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.modelapi.constructionshare.dto.filter.ConstructionShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapCSToConstructionShareFilter {

    ConstructionShareFilter from(ConstructionShareFilterDto filterDto);

}
