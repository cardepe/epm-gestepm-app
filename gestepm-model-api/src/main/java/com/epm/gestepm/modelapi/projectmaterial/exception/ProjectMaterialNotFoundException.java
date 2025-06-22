package com.epm.gestepm.modelapi.projectmaterial.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectMaterialNotFoundException extends RuntimeException {

  private final Integer id;

}
