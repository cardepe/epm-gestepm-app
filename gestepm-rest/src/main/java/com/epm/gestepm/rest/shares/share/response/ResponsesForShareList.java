package com.epm.gestepm.rest.shares.share.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.Share;
import com.epm.gestepm.restapi.openapi.model.ListSharesV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForShareList {

  default ResponseEntity<ListSharesV1200Response> toListSharesV1200Response(List<Share> data) {

    final ListSharesV1200Response response = new ListSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListSharesV1200Response> toListSharesV1200Response(APIMetadata metadata, List<Share> data) {

    if (metadata == null) {
      return toListSharesV1200Response(data);
    }

    final ListSharesV1200Response response = new ListSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListSharesV1200Response> toListSharesV1200Response(APIMetadata metadata, List<Share> data,
      Object etag) {

    if (etag == null) {
      return toListSharesV1200Response(metadata, data);
    }

    final ListSharesV1200Response response = new ListSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
