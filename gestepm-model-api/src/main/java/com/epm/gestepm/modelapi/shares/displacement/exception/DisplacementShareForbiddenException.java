package com.epm.gestepm.modelapi.shares.displacement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplacementShareForbiddenException extends RuntimeException {

  private final Integer id;

}
