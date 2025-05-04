package com.epm.gestepm.rest.shares.work.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.WorkShare;
import com.epm.gestepm.restapi.openapi.model.CreateWorkShareV1200Response;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForWorkShare {

  default ResponseEntity<CreateWorkShareV1200Response> toResWorkShareResponse(WorkShare data) {

    final CreateWorkShareV1200Response response = new CreateWorkShareV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateWorkShareV1200Response> toResWorkShareResponse(APIMetadata metadata, WorkShare data) {

    if (metadata == null) {
      return toResWorkShareResponse(data);
    }

    final CreateWorkShareV1200Response response = new CreateWorkShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateWorkShareV1200Response> toResWorkShareResponse(APIMetadata metadata, WorkShare data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResWorkShareResponse(metadata, data);
    }

    final CreateWorkShareV1200Response response = new CreateWorkShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
