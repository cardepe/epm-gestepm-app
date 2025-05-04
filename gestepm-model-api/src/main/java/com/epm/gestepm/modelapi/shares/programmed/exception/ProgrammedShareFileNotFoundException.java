package com.epm.gestepm.modelapi.shares.programmed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgrammedShareFileNotFoundException extends RuntimeException {

  private final Integer id;

}
