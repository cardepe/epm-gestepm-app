package com.epm.gestepm.rest.shares.share.mappers;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import com.epm.gestepm.restapi.openapi.model.Share;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapSToShareResponse {

  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "projectId", target = "project.id")
  Share from(ShareDto dto);

  List<Share> from(Page<ShareDto> list);

}
