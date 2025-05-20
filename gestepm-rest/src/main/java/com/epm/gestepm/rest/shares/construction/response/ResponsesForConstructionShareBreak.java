package com.epm.gestepm.rest.shares.construction.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateConstructionShareBreakV1200Response;
import com.epm.gestepm.restapi.openapi.model.ShareBreak;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForConstructionShareBreak {

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResConstructionShareBreakResponse(ShareBreak data) {

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResConstructionShareBreakResponse(APIMetadata metadata, ShareBreak data) {

    if (metadata == null) {
      return toResConstructionShareBreakResponse(data);
    }

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResConstructionShareBreakResponse(APIMetadata metadata, ShareBreak data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResConstructionShareBreakResponse(metadata, data);
    }

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
