package com.epm.gestepm.rest.shares.noprogrammed.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.NoProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.ResNoProgrammedShare;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForNoProgrammedShare {

  default ResponseEntity<ResNoProgrammedShare> toResNoProgrammedShareResponse(NoProgrammedShare data) {

    final ResNoProgrammedShare response = new ResNoProgrammedShare();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResNoProgrammedShare> toResNoProgrammedShareResponse(APIMetadata metadata, NoProgrammedShare data) {

    if (metadata == null) {
      return toResNoProgrammedShareResponse(data);
    }

    final ResNoProgrammedShare response = new ResNoProgrammedShare();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResNoProgrammedShare> toResNoProgrammedShareResponse(APIMetadata metadata, NoProgrammedShare data,
      Object etag) {

    if (etag == null) {
      return toResNoProgrammedShareResponse(metadata, data);
    }

    final ResNoProgrammedShare response = new ResNoProgrammedShare();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
