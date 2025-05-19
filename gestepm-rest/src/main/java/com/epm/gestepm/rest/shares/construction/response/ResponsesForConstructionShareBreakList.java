package com.epm.gestepm.rest.shares.construction.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListConstructionShareBreaksV1200Response;
import com.epm.gestepm.restapi.openapi.model.ShareBreak;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForConstructionShareBreakList {

  default ResponseEntity<ListConstructionShareBreaksV1200Response> toListConstructionShareBreaksV1200Response(List<ShareBreak> data) {

    final ListConstructionShareBreaksV1200Response response = new ListConstructionShareBreaksV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListConstructionShareBreaksV1200Response> toListConstructionShareBreaksV1200Response(APIMetadata metadata, List<ShareBreak> data) {

    if (metadata == null) {
      return toListConstructionShareBreaksV1200Response(data);
    }

    final ListConstructionShareBreaksV1200Response response = new ListConstructionShareBreaksV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListConstructionShareBreaksV1200Response> toListConstructionShareBreaksV1200Response(APIMetadata metadata, List<ShareBreak> data,
      Object etag) {

    if (etag == null) {
      return toListConstructionShareBreaksV1200Response(metadata, data);
    }

    final ListConstructionShareBreaksV1200Response response = new ListConstructionShareBreaksV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
