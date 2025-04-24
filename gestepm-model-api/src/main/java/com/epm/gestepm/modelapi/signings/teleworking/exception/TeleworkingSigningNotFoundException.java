package com.epm.gestepm.modelapi.signings.teleworking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeleworkingSigningNotFoundException extends RuntimeException {

  private final Integer id;

}
