package com.epm.gestepm.modelapi.shares.noprogrammed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoProgrammedShareForbiddenException extends RuntimeException {

  private final Integer id;

  private final String subRole;

}
