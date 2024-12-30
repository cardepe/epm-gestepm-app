package com.epm.gestepm.model.personalexpensesheet.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class PersonalExpenseSheet implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private Integer projectId;

  @NotNull
  private String description;

  @NotNull
  private OffsetDateTime startDate;

  @NotNull
  private PersonalExpenseSheetStatusEnum status;

  private String observations;

  private List<Integer> personalExpenseIds;

}
