package com.epm.gestepm.rest.shares.noprogrammed.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.NoProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.ResNoProgrammedShareList;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForNoProgrammedShareList {

  default ResponseEntity<ResNoProgrammedShareList> toResNoProgrammedShareListResponse(List<NoProgrammedShare> data) {

    final ResNoProgrammedShareList response = new ResNoProgrammedShareList();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResNoProgrammedShareList> toResNoProgrammedShareListResponse(APIMetadata metadata, List<NoProgrammedShare> data) {

    if (metadata == null) {
      return toResNoProgrammedShareListResponse(data);
    }

    final ResNoProgrammedShareList response = new ResNoProgrammedShareList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResNoProgrammedShareList> toResNoProgrammedShareListResponse(APIMetadata metadata, List<NoProgrammedShare> data,
      Object etag) {

    if (etag == null) {
      return toResNoProgrammedShareListResponse(metadata, data);
    }

    final ResNoProgrammedShareList response = new ResNoProgrammedShareList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
