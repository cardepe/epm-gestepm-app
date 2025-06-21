package com.epm.gestepm.modelapi.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {

  @NotNull
  private final Integer projectId;

}
