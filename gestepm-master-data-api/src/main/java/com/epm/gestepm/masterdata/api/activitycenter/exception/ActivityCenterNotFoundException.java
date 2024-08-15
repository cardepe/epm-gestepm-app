package com.epm.gestepm.masterdata.api.activitycenter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivityCenterNotFoundException extends RuntimeException {

  private final Integer id;

}
