package com.epm.gestepm.rest.shares.construction.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateConstructionShareV1200Response;
import com.epm.gestepm.restapi.openapi.model.ConstructionShare;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForConstructionShare {

  default ResponseEntity<CreateConstructionShareV1200Response> toResConstructionShareResponse(ConstructionShare data) {

    final CreateConstructionShareV1200Response response = new CreateConstructionShareV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareV1200Response> toResConstructionShareResponse(APIMetadata metadata, ConstructionShare data) {

    if (metadata == null) {
      return toResConstructionShareResponse(data);
    }

    final CreateConstructionShareV1200Response response = new CreateConstructionShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareV1200Response> toResConstructionShareResponse(APIMetadata metadata, ConstructionShare data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResConstructionShareResponse(metadata, data);
    }

    final CreateConstructionShareV1200Response response = new CreateConstructionShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
