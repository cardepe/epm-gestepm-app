package com.epm.gestepm.rest.personalexpensesheet.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.PersonalExpenseSheet;
import com.epm.gestepm.restapi.openapi.model.ListPersonalExpenseSheetsV1200Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForPersonalExpenseSheetList {

  default ResponseEntity<ListPersonalExpenseSheetsV1200Response> toListPersonalExpenseSheetsV1200Response(List<PersonalExpenseSheet> data) {

    final ListPersonalExpenseSheetsV1200Response response = new ListPersonalExpenseSheetsV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListPersonalExpenseSheetsV1200Response> toListPersonalExpenseSheetsV1200Response(APIMetadata metadata, List<PersonalExpenseSheet> data) {

    if (metadata == null) {
      return toListPersonalExpenseSheetsV1200Response(data);
    }

    final ListPersonalExpenseSheetsV1200Response response = new ListPersonalExpenseSheetsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListPersonalExpenseSheetsV1200Response> toListPersonalExpenseSheetsV1200Response(APIMetadata metadata, List<PersonalExpenseSheet> data,
      Object etag) {

    if (etag == null) {
      return toListPersonalExpenseSheetsV1200Response(metadata, data);
    }

    final ListPersonalExpenseSheetsV1200Response response = new ListPersonalExpenseSheetsV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
