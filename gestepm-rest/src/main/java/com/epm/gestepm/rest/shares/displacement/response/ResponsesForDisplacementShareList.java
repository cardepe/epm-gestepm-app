package com.epm.gestepm.rest.shares.displacement.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListDisplacementSharesV1200Response;
import com.epm.gestepm.restapi.openapi.model.DisplacementShare;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForDisplacementShareList {

  default ResponseEntity<ListDisplacementSharesV1200Response> toListDisplacementSharesV1200Response(List<DisplacementShare> data) {

    final ListDisplacementSharesV1200Response response = new ListDisplacementSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListDisplacementSharesV1200Response> toListDisplacementSharesV1200Response(APIMetadata metadata, List<DisplacementShare> data) {

    if (metadata == null) {
      return toListDisplacementSharesV1200Response(data);
    }

    final ListDisplacementSharesV1200Response response = new ListDisplacementSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListDisplacementSharesV1200Response> toListDisplacementSharesV1200Response(APIMetadata metadata, List<DisplacementShare> data,
      Object etag) {

    if (etag == null) {
      return toListDisplacementSharesV1200Response(metadata, data);
    }

    final ListDisplacementSharesV1200Response response = new ListDisplacementSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
