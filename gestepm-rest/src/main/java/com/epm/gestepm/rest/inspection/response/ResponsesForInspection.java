package com.epm.gestepm.rest.inspection.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateInspectionV1200Response;
import com.epm.gestepm.restapi.openapi.model.Inspection;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForInspection {

  default ResponseEntity<CreateInspectionV1200Response> toResInspectionResponse(Inspection data) {

    final CreateInspectionV1200Response response = new CreateInspectionV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateInspectionV1200Response> toResInspectionResponse(APIMetadata metadata, Inspection data) {

    if (metadata == null) {
      return toResInspectionResponse(data);
    }

    final CreateInspectionV1200Response response = new CreateInspectionV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateInspectionV1200Response> toResInspectionResponse(APIMetadata metadata, Inspection data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResInspectionResponse(metadata, data);
    }

    final CreateInspectionV1200Response response = new CreateInspectionV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
