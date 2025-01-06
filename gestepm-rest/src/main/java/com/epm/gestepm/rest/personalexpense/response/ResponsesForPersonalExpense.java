package com.epm.gestepm.rest.personalexpense.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreatePersonalExpenseV1200Response;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForPersonalExpense {

  default ResponseEntity<CreatePersonalExpenseV1200Response> toResPersonalExpenseResponse(PersonalExpense data) {

    final CreatePersonalExpenseV1200Response response = new CreatePersonalExpenseV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreatePersonalExpenseV1200Response> toResPersonalExpenseResponse(APIMetadata metadata, PersonalExpense data) {

    if (metadata == null) {
      return toResPersonalExpenseResponse(data);
    }

    final CreatePersonalExpenseV1200Response response = new CreatePersonalExpenseV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreatePersonalExpenseV1200Response> toResPersonalExpenseResponse(APIMetadata metadata, PersonalExpense data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResPersonalExpenseResponse(metadata, data);
    }

    final CreatePersonalExpenseV1200Response response = new CreatePersonalExpenseV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
