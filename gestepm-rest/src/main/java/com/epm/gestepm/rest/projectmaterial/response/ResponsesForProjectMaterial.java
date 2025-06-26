package com.epm.gestepm.rest.projectmaterial.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreateProjectMaterialV1200Response;
import com.epm.gestepm.restapi.openapi.model.CreateProjectMaterialV1200Response;
import com.epm.gestepm.restapi.openapi.model.ListProjectMaterialsV1200Response;
import com.epm.gestepm.restapi.openapi.model.ProjectMaterial;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProjectMaterial {

  default ResponseEntity<CreateProjectMaterialV1200Response> toResProjectMaterialResponse(ProjectMaterial data) {

    final CreateProjectMaterialV1200Response response = new CreateProjectMaterialV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProjectMaterialV1200Response> toResProjectMaterialResponse(APIMetadata metadata, ProjectMaterial data) {

    if (metadata == null) {
      return toResProjectMaterialResponse(data);
    }

    final CreateProjectMaterialV1200Response response = new CreateProjectMaterialV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProjectMaterialV1200Response> toResProjectMaterialResponse(APIMetadata metadata, ProjectMaterial data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResProjectMaterialResponse(metadata, data);
    }

    final CreateProjectMaterialV1200Response response = new CreateProjectMaterialV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
