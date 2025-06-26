package com.epm.gestepm.rest.customer.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.Customer;
import com.epm.gestepm.restapi.openapi.model.FindCustomerByProjectIdV1200Response;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForCustomer {

  default ResponseEntity<FindCustomerByProjectIdV1200Response> toResCustomerResponse(Customer data) {

    final FindCustomerByProjectIdV1200Response response = new FindCustomerByProjectIdV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<FindCustomerByProjectIdV1200Response> toResCustomerResponse(APIMetadata metadata, Customer data) {

    if (metadata == null) {
      return toResCustomerResponse(data);
    }

    final FindCustomerByProjectIdV1200Response response = new FindCustomerByProjectIdV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<FindCustomerByProjectIdV1200Response> toResCustomerResponse(APIMetadata metadata, Customer data,
      Object etag) {

    if (etag == null) {
      return toResCustomerResponse(metadata, data);
    }

    final FindCustomerByProjectIdV1200Response response = new FindCustomerByProjectIdV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
