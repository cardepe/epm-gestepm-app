package com.epm.gestepm.modelapi.shares.breaks.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareBreakNotFoundException extends RuntimeException {

  private final Integer id;

}
