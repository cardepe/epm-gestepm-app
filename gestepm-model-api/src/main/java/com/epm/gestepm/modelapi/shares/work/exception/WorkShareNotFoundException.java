package com.epm.gestepm.modelapi.shares.work.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkShareNotFoundException extends RuntimeException {

  private final Integer id;

}
