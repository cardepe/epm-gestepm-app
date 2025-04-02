package com.epm.gestepm.modelapi.teleworkingsigning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeleworkingSigningNotFoundException extends RuntimeException {

  private final Integer id;

}
