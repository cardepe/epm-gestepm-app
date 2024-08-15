package com.epm.gestepm.rest.country.response;

import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.restapi.openapi.model.Country;
import com.epm.gestepm.restapi.openapi.model.ResCountryList;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForCountryList {

  default ResponseEntity<ResCountryList> toResCountryListResponse(List<Country> data) {

    final ResCountryList response = new ResCountryList();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResCountryList> toResCountryListResponse(APIMetadata metadata, List<Country> data) {

    if (metadata == null) {
      return toResCountryListResponse(data);
    }

    final ResCountryList response = new ResCountryList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResCountryList> toResCountryListResponse(APIMetadata metadata, List<Country> data,
      Object etag) {

    if (etag == null) {
      return toResCountryListResponse(metadata, data);
    }

    final ResCountryList response = new ResCountryList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
