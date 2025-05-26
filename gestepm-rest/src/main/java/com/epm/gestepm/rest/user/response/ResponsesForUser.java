package com.epm.gestepm.rest.user.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.User;
import com.epm.gestepm.restapi.openapi.model.CreateUserV1200Response;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForUser {

  default ResponseEntity<CreateUserV1200Response> toResUserResponse(User data) {

    final CreateUserV1200Response response = new CreateUserV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateUserV1200Response> toResUserResponse(APIMetadata metadata, User data) {

    if (metadata == null) {
      return toResUserResponse(data);
    }

    final CreateUserV1200Response response = new CreateUserV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateUserV1200Response> toResUserResponse(APIMetadata metadata, User data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResUserResponse(metadata, data);
    }

    final CreateUserV1200Response response = new CreateUserV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
