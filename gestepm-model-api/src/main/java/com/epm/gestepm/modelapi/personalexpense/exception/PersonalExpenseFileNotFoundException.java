package com.epm.gestepm.modelapi.personalexpense.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonalExpenseFileNotFoundException extends RuntimeException {

  private final Integer id;

}
