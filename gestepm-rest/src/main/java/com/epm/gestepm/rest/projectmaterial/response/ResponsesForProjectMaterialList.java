package com.epm.gestepm.rest.projectmaterial.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListProjectMaterialsV1200Response;
import com.epm.gestepm.restapi.openapi.model.ProjectMaterial;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProjectMaterialList {

  default ResponseEntity<ListProjectMaterialsV1200Response> toListProjectMaterialsV1200Response(List<ProjectMaterial> data) {

    final ListProjectMaterialsV1200Response response = new ListProjectMaterialsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProjectMaterialsV1200Response> toListProjectMaterialsV1200Response(APIMetadata metadata, List<ProjectMaterial> data) {

    if (metadata == null) {
      return toListProjectMaterialsV1200Response(data);
    }

    final ListProjectMaterialsV1200Response response = new ListProjectMaterialsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProjectMaterialsV1200Response> toListProjectMaterialsV1200Response(APIMetadata metadata, List<ProjectMaterial> data,
      Object etag) {

    if (etag == null) {
      return toListProjectMaterialsV1200Response(metadata, data);
    }

    final ListProjectMaterialsV1200Response response = new ListProjectMaterialsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
