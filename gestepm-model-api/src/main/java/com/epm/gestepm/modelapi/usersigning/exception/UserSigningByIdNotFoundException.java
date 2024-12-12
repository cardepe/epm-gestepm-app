package com.epm.gestepm.modelapi.usersigning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSigningByIdNotFoundException extends RuntimeException {

  private final Integer id;

}
