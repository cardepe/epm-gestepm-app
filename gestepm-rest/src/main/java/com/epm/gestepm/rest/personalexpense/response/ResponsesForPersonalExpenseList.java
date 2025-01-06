package com.epm.gestepm.rest.personalexpense.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.ListPersonalExpensesV1200Response;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForPersonalExpenseList {

  default ResponseEntity<ListPersonalExpensesV1200Response> toListPersonalExpensesV1200Response(List<PersonalExpense> data) {

    final ListPersonalExpensesV1200Response response = new ListPersonalExpensesV1200Response();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListPersonalExpensesV1200Response> toListPersonalExpensesV1200Response(APIMetadata metadata, List<PersonalExpense> data) {

    if (metadata == null) {
      return toListPersonalExpensesV1200Response(data);
    }

    final ListPersonalExpensesV1200Response response = new ListPersonalExpensesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ListPersonalExpensesV1200Response> toListPersonalExpensesV1200Response(APIMetadata metadata, List<PersonalExpense> data,
      Object etag) {

    if (etag == null) {
      return toListPersonalExpensesV1200Response(metadata, data);
    }

    final ListPersonalExpensesV1200Response response = new ListPersonalExpensesV1200Response();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
