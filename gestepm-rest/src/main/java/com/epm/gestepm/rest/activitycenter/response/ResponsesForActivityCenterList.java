package com.epm.gestepm.rest.activitycenter.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import com.epm.gestepm.restapi.openapi.model.ResActivityCenterList;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForActivityCenterList {

  default ResponseEntity<ResActivityCenterList> toResActivityCenterListResponse(List<ActivityCenter> data) {

    final ResActivityCenterList response = new ResActivityCenterList();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResActivityCenterList> toResActivityCenterListResponse(APIMetadata metadata, List<ActivityCenter> data) {

    if (metadata == null) {
      return toResActivityCenterListResponse(data);
    }

    final ResActivityCenterList response = new ResActivityCenterList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResActivityCenterList> toResActivityCenterListResponse(APIMetadata metadata, List<ActivityCenter> data,
      Object etag) {

    if (etag == null) {
      return toResActivityCenterListResponse(metadata, data);
    }

    final ResActivityCenterList response = new ResActivityCenterList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
