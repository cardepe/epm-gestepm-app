package com.epm.gestepm.masterdata.activitycenter.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ActivityCenter implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private Integer countryId;

}
