package com.epm.gestepm.modelapi.shares.work.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkShareFileNotFoundException extends RuntimeException {

  private final Integer id;

}
