package com.epm.gestepm.masterdata.displacement.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Displacement implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer activityCenterId;

  @NotNull
  private String name;

  @NotNull
  private DisplacementType type;

  @NotNull
  private Integer totalTime;

}
