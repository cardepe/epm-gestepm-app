package com.epm.gestepm.rest.country.response;

import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.restapi.openapi.model.Country;
import com.epm.gestepm.restapi.openapi.model.ResCountry;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForCountry {

  default ResponseEntity<ResCountry> toResCountryResponse(Country data) {

    final ResCountry response = new ResCountry();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResCountry> toResCountryResponse(APIMetadata metadata, Country data) {

    if (metadata == null) {
      return toResCountryResponse(data);
    }

    final ResCountry response = new ResCountry();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResCountry> toResCountryResponse(APIMetadata metadata, Country data,
      Object etag) {

    if (etag == null) {
      return toResCountryResponse(metadata, data);
    }

    final ResCountry response = new ResCountry();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
