package com.epm.gestepm.masterdata.country.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Country implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private String name;

  @NotNull
  private String tag;

}
