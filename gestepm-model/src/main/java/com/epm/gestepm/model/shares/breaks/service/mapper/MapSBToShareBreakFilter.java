package com.epm.gestepm.model.shares.breaks.service.mapper;

import com.epm.gestepm.model.shares.breaks.dao.entity.filter.ShareBreakFilter;
import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSBToShareBreakFilter {

    ShareBreakFilter from(ShareBreakFilterDto filterDto);

}
