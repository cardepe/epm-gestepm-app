package com.epm.gestepm.masterdata.api.holiday.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HolidayNotFoundException extends RuntimeException {

  private final Integer id;

}
