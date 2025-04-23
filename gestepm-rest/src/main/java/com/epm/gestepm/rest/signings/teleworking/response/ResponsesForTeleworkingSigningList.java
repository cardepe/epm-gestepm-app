package com.epm.gestepm.rest.signings.teleworking.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListTeleworkingSigningsV1200Response;
import com.epm.gestepm.restapi.openapi.model.TeleworkingSigning;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForTeleworkingSigningList {

  default ResponseEntity<ListTeleworkingSigningsV1200Response> toListTeleworkingSigningsV1200Response(List<TeleworkingSigning> data) {

    final ListTeleworkingSigningsV1200Response response = new ListTeleworkingSigningsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListTeleworkingSigningsV1200Response> toListTeleworkingSigningsV1200Response(APIMetadata metadata, List<TeleworkingSigning> data) {

    if (metadata == null) {
      return toListTeleworkingSigningsV1200Response(data);
    }

    final ListTeleworkingSigningsV1200Response response = new ListTeleworkingSigningsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListTeleworkingSigningsV1200Response> toListTeleworkingSigningsV1200Response(APIMetadata metadata, List<TeleworkingSigning> data,
      Object etag) {

    if (etag == null) {
      return toListTeleworkingSigningsV1200Response(metadata, data);
    }

    final ListTeleworkingSigningsV1200Response response = new ListTeleworkingSigningsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
