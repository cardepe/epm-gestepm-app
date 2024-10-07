package com.epm.gestepm.rest.holiday.response;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.restapi.openapi.model.Holiday;
import com.epm.gestepm.restapi.openapi.model.ResHolidayList;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

public interface ResponsesForHolidayList {

  default ResponseEntity<ResHolidayList> toResHolidayListResponse(List<Holiday> data) {

    final ResHolidayList response = new ResHolidayList();
    response.setData(data);

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResHolidayList> toResHolidayListResponse(APIMetadata metadata, List<Holiday> data) {

    if (metadata == null) {
      return toResHolidayListResponse(data);
    }

    final ResHolidayList response = new ResHolidayList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().body(response);
  }

  default ResponseEntity<ResHolidayList> toResHolidayListResponse(APIMetadata metadata, List<Holiday> data,
      Object etag) {

    if (etag == null) {
      return toResHolidayListResponse(metadata, data);
    }

    final ResHolidayList response = new ResHolidayList();
    response.setData(data);
    response.setMetadata(getMapper(MetadataMapper.class).from(metadata));

    return ResponseEntity.ok().eTag(String.valueOf(etag)).body(response);
  }

}
