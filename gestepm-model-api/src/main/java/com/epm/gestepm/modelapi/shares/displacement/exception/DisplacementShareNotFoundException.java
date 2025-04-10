package com.epm.gestepm.modelapi.shares.displacement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplacementShareNotFoundException extends RuntimeException {

  private final Integer id;

}
