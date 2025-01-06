package com.epm.gestepm.rest.personalexpensesheet.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.CreatePersonalExpenseSheetV1200Response;
import com.epm.gestepm.restapi.openapi.model.PersonalExpenseSheet;
import org.springframework.http.ResponseEntity;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForPersonalExpenseSheet {

  default ResponseEntity<CreatePersonalExpenseSheetV1200Response> toResPersonalExpenseSheetResponse(PersonalExpenseSheet data) {

    final CreatePersonalExpenseSheetV1200Response response = new CreatePersonalExpenseSheetV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreatePersonalExpenseSheetV1200Response> toResPersonalExpenseSheetResponse(APIMetadata metadata, PersonalExpenseSheet data) {

    if (metadata == null) {
      return toResPersonalExpenseSheetResponse(data);
    }

    final CreatePersonalExpenseSheetV1200Response response = new CreatePersonalExpenseSheetV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<CreatePersonalExpenseSheetV1200Response> toResPersonalExpenseSheetResponse(APIMetadata metadata, PersonalExpenseSheet data,
                                                                                              Object etag) {

    if (etag == null) {
      return toResPersonalExpenseSheetResponse(metadata, data);
    }

    final CreatePersonalExpenseSheetV1200Response response = new CreatePersonalExpenseSheetV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
