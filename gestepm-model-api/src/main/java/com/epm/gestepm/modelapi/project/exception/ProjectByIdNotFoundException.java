package com.epm.gestepm.modelapi.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectByIdNotFoundException extends RuntimeException {

  private final Integer id;

}
