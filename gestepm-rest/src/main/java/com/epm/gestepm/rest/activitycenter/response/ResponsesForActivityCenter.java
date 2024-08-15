package com.epm.gestepm.rest.activitycenter.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import com.epm.gestepm.restapi.openapi.model.ResActivityCenter;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForActivityCenter {

  default ResponseEntity<ResActivityCenter> toResActivityCenterResponse(ActivityCenter data) {

    final ResActivityCenter response = new ResActivityCenter();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResActivityCenter> toResActivityCenterResponse(APIMetadata metadata, ActivityCenter data) {

    if (metadata == null) {
      return toResActivityCenterResponse(data);
    }

    final ResActivityCenter response = new ResActivityCenter();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResActivityCenter> toResActivityCenterResponse(APIMetadata metadata, ActivityCenter data,
      Object etag) {

    if (etag == null) {
      return toResActivityCenterResponse(metadata, data);
    }

    final ResActivityCenter response = new ResActivityCenter();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
