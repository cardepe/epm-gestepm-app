package com.epm.gestepm.model.shares.work.service.mapper;

import com.epm.gestepm.model.shares.work.dao.entity.filter.WorkShareFilter;
import com.epm.gestepm.modelapi.shares.work.dto.filter.WorkShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapWSToWorkShareFilter {

    WorkShareFilter from(WorkShareFilterDto filterDto);

}
