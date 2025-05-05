package com.epm.gestepm.model.shares.share.service.mapper;

import com.epm.gestepm.model.shares.share.dao.entity.filter.ShareFilter;
import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;
import org.mapstruct.Mapper;

@Mapper
public interface MapSToShareFilter {

    ShareFilter from(ShareFilterDto filterDto);

}
