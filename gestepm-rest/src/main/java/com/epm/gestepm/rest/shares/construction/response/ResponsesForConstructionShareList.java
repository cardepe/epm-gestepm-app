package com.epm.gestepm.rest.shares.construction.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ConstructionShare;
import com.epm.gestepm.restapi.openapi.model.ListConstructionSharesV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForConstructionShareList {

  default ResponseEntity<ListConstructionSharesV1200Response> toListConstructionSharesV1200Response(List<ConstructionShare> data) {

    final ListConstructionSharesV1200Response response = new ListConstructionSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListConstructionSharesV1200Response> toListConstructionSharesV1200Response(APIMetadata metadata, List<ConstructionShare> data) {

    if (metadata == null) {
      return toListConstructionSharesV1200Response(data);
    }

    final ListConstructionSharesV1200Response response = new ListConstructionSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListConstructionSharesV1200Response> toListConstructionSharesV1200Response(APIMetadata metadata, List<ConstructionShare> data,
      Object etag) {

    if (etag == null) {
      return toListConstructionSharesV1200Response(metadata, data);
    }

    final ListConstructionSharesV1200Response response = new ListConstructionSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
