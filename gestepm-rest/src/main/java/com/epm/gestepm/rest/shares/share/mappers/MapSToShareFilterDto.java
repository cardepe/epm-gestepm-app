package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;
import com.epm.gestepm.rest.shares.share.request.ShareListRestRequest;
import org.mapstruct.Mapper;

@Mapper
public interface MapSToShareFilterDto {

  ShareFilterDto from(ShareListRestRequest req);

}
