package com.epm.gestepm.rest.displacement.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.Displacement;
import com.epm.gestepm.restapi.openapi.model.ResDisplacement;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForDisplacement {

  default ResponseEntity<ResDisplacement> toResDisplacementResponse(Displacement data) {

    final ResDisplacement response = new ResDisplacement();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResDisplacement> toResDisplacementResponse(APIMetadata metadata, Displacement data) {

    if (metadata == null) {
      return toResDisplacementResponse(data);
    }

    final ResDisplacement response = new ResDisplacement();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResDisplacement> toResDisplacementResponse(APIMetadata metadata, Displacement data,
      Object etag) {

    if (etag == null) {
      return toResDisplacementResponse(metadata, data);
    }

    final ResDisplacement response = new ResDisplacement();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
