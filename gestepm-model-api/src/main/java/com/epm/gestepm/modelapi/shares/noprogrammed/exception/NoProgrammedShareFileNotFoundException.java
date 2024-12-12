package com.epm.gestepm.modelapi.shares.noprogrammed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoProgrammedShareFileNotFoundException extends RuntimeException {

  private final Integer id;

}
