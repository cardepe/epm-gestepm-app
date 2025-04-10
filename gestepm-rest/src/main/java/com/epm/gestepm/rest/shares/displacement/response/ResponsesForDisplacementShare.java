package com.epm.gestepm.rest.shares.displacement.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateDisplacementShareV1200Response;
import com.epm.gestepm.restapi.openapi.model.DisplacementShare;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForDisplacementShare {

  default ResponseEntity<CreateDisplacementShareV1200Response> toResDisplacementShareResponse(DisplacementShare data) {

    final CreateDisplacementShareV1200Response response = new CreateDisplacementShareV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateDisplacementShareV1200Response> toResDisplacementShareResponse(APIMetadata metadata, DisplacementShare data) {

    if (metadata == null) {
      return toResDisplacementShareResponse(data);
    }

    final CreateDisplacementShareV1200Response response = new CreateDisplacementShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateDisplacementShareV1200Response> toResDisplacementShareResponse(APIMetadata metadata, DisplacementShare data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResDisplacementShareResponse(metadata, data);
    }

    final CreateDisplacementShareV1200Response response = new CreateDisplacementShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
