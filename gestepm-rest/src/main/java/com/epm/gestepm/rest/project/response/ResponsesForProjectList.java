package com.epm.gestepm.rest.project.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListProjectsV1200Response;
import com.epm.gestepm.restapi.openapi.model.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProjectList {

  default ResponseEntity<ListProjectsV1200Response> toListProjectsV1200Response(List<Project> data) {

    final ListProjectsV1200Response response = new ListProjectsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProjectsV1200Response> toListProjectsV1200Response(APIMetadata metadata, List<Project> data) {

    if (metadata == null) {
      return toListProjectsV1200Response(data);
    }

    final ListProjectsV1200Response response = new ListProjectsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProjectsV1200Response> toListProjectsV1200Response(APIMetadata metadata, List<Project> data,
      Object etag) {

    if (etag == null) {
      return toListProjectsV1200Response(metadata, data);
    }

    final ListProjectsV1200Response response = new ListProjectsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
