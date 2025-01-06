package com.epm.gestepm.modelapi.personalexpense.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonalExpenseNotFoundException extends RuntimeException {

  private final Integer id;

}
