package com.epm.gestepm.rest.shares.work.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.WorkShare;
import com.epm.gestepm.restapi.openapi.model.ListWorkSharesV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForWorkShareList {

  default ResponseEntity<ListWorkSharesV1200Response> toListWorkSharesV1200Response(List<WorkShare> data) {

    final ListWorkSharesV1200Response response = new ListWorkSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListWorkSharesV1200Response> toListWorkSharesV1200Response(APIMetadata metadata, List<WorkShare> data) {

    if (metadata == null) {
      return toListWorkSharesV1200Response(data);
    }

    final ListWorkSharesV1200Response response = new ListWorkSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListWorkSharesV1200Response> toListWorkSharesV1200Response(APIMetadata metadata, List<WorkShare> data,
      Object etag) {

    if (etag == null) {
      return toListWorkSharesV1200Response(metadata, data);
    }

    final ListWorkSharesV1200Response response = new ListWorkSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
