package com.epm.gestepm.model.timecontrol.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TimeControl implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private TimeControlTypeEnum type;

  private String description;

  @NotNull
  private LocalDateTime startDate;

  @NotNull
  private LocalDateTime endDate;

}
