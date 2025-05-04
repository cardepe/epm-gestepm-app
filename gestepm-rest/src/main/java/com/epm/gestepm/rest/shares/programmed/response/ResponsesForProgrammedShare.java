package com.epm.gestepm.rest.shares.programmed.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.CreateProgrammedShareV1200Response;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProgrammedShare {

  default ResponseEntity<CreateProgrammedShareV1200Response> toResProgrammedShareResponse(ProgrammedShare data) {

    final CreateProgrammedShareV1200Response response = new CreateProgrammedShareV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProgrammedShareV1200Response> toResProgrammedShareResponse(APIMetadata metadata, ProgrammedShare data) {

    if (metadata == null) {
      return toResProgrammedShareResponse(data);
    }

    final CreateProgrammedShareV1200Response response = new CreateProgrammedShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreateProgrammedShareV1200Response> toResProgrammedShareResponse(APIMetadata metadata, ProgrammedShare data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResProgrammedShareResponse(metadata, data);
    }

    final CreateProgrammedShareV1200Response response = new CreateProgrammedShareV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
