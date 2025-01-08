package com.epm.gestepm.model.personalexpensesheet.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
  private LocalDateTime startDate;

  @NotNull
  private PersonalExpenseSheetStatusEnum status;

  private String observations;

  private Double amount;

  private List<Integer> personalExpenseIds;

}
