package com.epm.gestepm.rest.user.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.User;
import com.epm.gestepm.restapi.openapi.model.ListUsersV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForUserList {

  default ResponseEntity<ListUsersV1200Response> toListUsersV1200Response(List<User> data) {

    final ListUsersV1200Response response = new ListUsersV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListUsersV1200Response> toListUsersV1200Response(APIMetadata metadata, List<User> data) {

    if (metadata == null) {
      return toListUsersV1200Response(data);
    }

    final ListUsersV1200Response response = new ListUsersV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListUsersV1200Response> toListUsersV1200Response(APIMetadata metadata, List<User> data,
      Object etag) {

    if (etag == null) {
      return toListUsersV1200Response(metadata, data);
    }

    final ListUsersV1200Response response = new ListUsersV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
