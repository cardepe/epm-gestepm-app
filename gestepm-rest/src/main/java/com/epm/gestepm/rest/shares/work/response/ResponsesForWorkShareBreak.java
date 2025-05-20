package com.epm.gestepm.rest.shares.work.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateConstructionShareBreakV1200Response;
import com.epm.gestepm.restapi.openapi.model.ShareBreak;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForWorkShareBreak {

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResWorkShareBreakResponse(ShareBreak data) {

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResWorkShareBreakResponse(APIMetadata metadata, ShareBreak data) {

    if (metadata == null) {
      return toResWorkShareBreakResponse(data);
    }

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateConstructionShareBreakV1200Response> toResWorkShareBreakResponse(APIMetadata metadata, ShareBreak data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResWorkShareBreakResponse(metadata, data);
    }

    final CreateConstructionShareBreakV1200Response response = new CreateConstructionShareBreakV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
