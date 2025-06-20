package com.epm.gestepm.rest.project.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateProjectV1200Response;
import com.epm.gestepm.restapi.openapi.model.Project;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProject {

  default ResponseEntity<CreateProjectV1200Response> toResProjectResponse(Project data) {

    final CreateProjectV1200Response response = new CreateProjectV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProjectV1200Response> toResProjectResponse(APIMetadata metadata, Project data) {

    if (metadata == null) {
      return toResProjectResponse(data);
    }

    final CreateProjectV1200Response response = new CreateProjectV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProjectV1200Response> toResProjectResponse(APIMetadata metadata, Project data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResProjectResponse(metadata, data);
    }

    final CreateProjectV1200Response response = new CreateProjectV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
