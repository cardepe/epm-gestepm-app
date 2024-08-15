package com.epm.gestepm.masterdata.api.country.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryNotFoundException extends RuntimeException {

  private final Integer id;

}
