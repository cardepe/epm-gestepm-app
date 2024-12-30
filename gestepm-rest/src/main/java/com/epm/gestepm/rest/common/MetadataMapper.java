package com.epm.gestepm.rest.common;

import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.restapi.openapi.model.Meta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper
public interface MetadataMapper {

  @Named("longToTime")
  static LocalDateTime longToTime(Long value) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
  }

  @Mapping(source = "request.requestTimestamp", target = "request.requestTimestamp", qualifiedByName = "longToTime")
  @Mapping(source = "request.responseTimestamp", target = "request.responseTimestamp", qualifiedByName = "longToTime")
  Meta from(APIMetadata from);

}
