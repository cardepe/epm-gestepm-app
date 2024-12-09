package com.epm.gestepm.rest.shares.noprogrammed.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.NoProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.ListNoProgrammedSharesV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForNoProgrammedShareList {

  default ResponseEntity<ListNoProgrammedSharesV1200Response> toListNoProgrammedSharesV1200Response(List<NoProgrammedShare> data) {

    final ListNoProgrammedSharesV1200Response response = new ListNoProgrammedSharesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListNoProgrammedSharesV1200Response> toListNoProgrammedSharesV1200Response(APIMetadata metadata, List<NoProgrammedShare> data) {

    if (metadata == null) {
      return toListNoProgrammedSharesV1200Response(data);
    }

    final ListNoProgrammedSharesV1200Response response = new ListNoProgrammedSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListNoProgrammedSharesV1200Response> toListNoProgrammedSharesV1200Response(APIMetadata metadata, List<NoProgrammedShare> data,
      Object etag) {

    if (etag == null) {
      return toListNoProgrammedSharesV1200Response(metadata, data);
    }

    final ListNoProgrammedSharesV1200Response response = new ListNoProgrammedSharesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
