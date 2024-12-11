package com.epm.gestepm.rest.inspection.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListInspectionsV1200Response;
import com.epm.gestepm.restapi.openapi.model.Inspection;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForInspectionList {

  default ResponseEntity<ListInspectionsV1200Response> toListInspectionsV1200Response(List<Inspection> data) {

    final ListInspectionsV1200Response response = new ListInspectionsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListInspectionsV1200Response> toListInspectionsV1200Response(APIMetadata metadata, List<Inspection> data) {

    if (metadata == null) {
      return toListInspectionsV1200Response(data);
    }

    final ListInspectionsV1200Response response = new ListInspectionsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListInspectionsV1200Response> toListInspectionsV1200Response(APIMetadata metadata, List<Inspection> data,
      Object etag) {

    if (etag == null) {
      return toListInspectionsV1200Response(metadata, data);
    }

    final ListInspectionsV1200Response response = new ListInspectionsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
