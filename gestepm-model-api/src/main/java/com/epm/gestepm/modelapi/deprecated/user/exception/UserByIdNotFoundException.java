package com.epm.gestepm.modelapi.deprecated.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserByIdNotFoundException extends RuntimeException {

  private final Integer id;

}
