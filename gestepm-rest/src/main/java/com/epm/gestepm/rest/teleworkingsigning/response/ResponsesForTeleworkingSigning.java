package com.epm.gestepm.rest.teleworkingsigning.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateTeleworkingSigningV1200Response;
import com.epm.gestepm.restapi.openapi.model.TeleworkingSigning;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForTeleworkingSigning {

  default ResponseEntity<CreateTeleworkingSigningV1200Response> toResTeleworkingSigningResponse(TeleworkingSigning data) {

    final CreateTeleworkingSigningV1200Response response = new CreateTeleworkingSigningV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateTeleworkingSigningV1200Response> toResTeleworkingSigningResponse(APIMetadata metadata, TeleworkingSigning data) {

    if (metadata == null) {
      return toResTeleworkingSigningResponse(data);
    }

    final CreateTeleworkingSigningV1200Response response = new CreateTeleworkingSigningV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateTeleworkingSigningV1200Response> toResTeleworkingSigningResponse(APIMetadata metadata, TeleworkingSigning data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResTeleworkingSigningResponse(metadata, data);
    }

    final CreateTeleworkingSigningV1200Response response = new CreateTeleworkingSigningV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
