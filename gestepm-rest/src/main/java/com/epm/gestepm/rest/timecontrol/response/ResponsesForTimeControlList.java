package com.epm.gestepm.rest.timecontrol.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListTimeControlsV1200Response;
import com.epm.gestepm.restapi.openapi.model.TimeControl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForTimeControlList {

  default ResponseEntity<ListTimeControlsV1200Response> toListTimeControlsV1200Response(List<TimeControl> data) {

    final ListTimeControlsV1200Response response = new ListTimeControlsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListTimeControlsV1200Response> toListTimeControlsV1200Response(APIMetadata metadata, List<TimeControl> data) {

    if (metadata == null) {
      return toListTimeControlsV1200Response(data);
    }

    final ListTimeControlsV1200Response response = new ListTimeControlsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListTimeControlsV1200Response> toListTimeControlsV1200Response(APIMetadata metadata, List<TimeControl> data,
      Object etag) {

    if (etag == null) {
      return toListTimeControlsV1200Response(metadata, data);
    }

    final ListTimeControlsV1200Response response = new ListTimeControlsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
