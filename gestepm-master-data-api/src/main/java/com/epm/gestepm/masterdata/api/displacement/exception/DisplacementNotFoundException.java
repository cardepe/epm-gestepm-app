package com.epm.gestepm.masterdata.api.displacement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplacementNotFoundException extends RuntimeException {

  private final Integer id;

}
