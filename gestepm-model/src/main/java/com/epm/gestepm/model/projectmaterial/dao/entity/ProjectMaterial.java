package com.epm.gestepm.model.projectmaterial.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProjectMaterial implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer projectId;

  @NotNull
  private String nameEs;

  @NotNull
  private String nameFr;

  @NotNull
  private Boolean required;

}
