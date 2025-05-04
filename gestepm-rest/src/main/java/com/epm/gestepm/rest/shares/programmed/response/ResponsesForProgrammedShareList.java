package com.epm.gestepm.rest.shares.programmed.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.ListProgrammedSharesV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForProgrammedShareList {

  default ResponseEntity<ListProgrammedSharesV1200Response> toListProgrammedSharesV1200Response(List<ProgrammedShare> data) {

    final ListProgrammedSharesV1200Response response = new ListProgrammedSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProgrammedSharesV1200Response> toListProgrammedSharesV1200Response(APIMetadata metadata, List<ProgrammedShare> data) {

    if (metadata == null) {
      return toListProgrammedSharesV1200Response(data);
    }

    final ListProgrammedSharesV1200Response response = new ListProgrammedSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListProgrammedSharesV1200Response> toListProgrammedSharesV1200Response(APIMetadata metadata, List<ProgrammedShare> data,
      Object etag) {

    if (etag == null) {
      return toListProgrammedSharesV1200Response(metadata, data);
    }

    final ListProgrammedSharesV1200Response response = new ListProgrammedSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
