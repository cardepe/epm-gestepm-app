package com.epm.gestepm.rest.displacement.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.Displacement;
import com.epm.gestepm.restapi.openapi.model.ResDisplacementList;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForDisplacementList {

  default ResponseEntity<ResDisplacementList> toResDisplacementListResponse(List<Displacement> data) {

    final ResDisplacementList response = new ResDisplacementList();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResDisplacementList> toResDisplacementListResponse(APIMetadata metadata, List<Displacement> data) {

    if (metadata == null) {
      return toResDisplacementListResponse(data);
    }

    final ResDisplacementList response = new ResDisplacementList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResDisplacementList> toResDisplacementListResponse(APIMetadata metadata, List<Displacement> data,
      Object etag) {

    if (etag == null) {
      return toResDisplacementListResponse(metadata, data);
    }

    final ResDisplacementList response = new ResDisplacementList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
