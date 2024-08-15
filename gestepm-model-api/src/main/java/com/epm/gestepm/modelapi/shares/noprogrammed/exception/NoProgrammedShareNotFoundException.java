package com.epm.gestepm.modelapi.shares.noprogrammed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoProgrammedShareNotFoundException extends RuntimeException {

  private final Integer id;

}
