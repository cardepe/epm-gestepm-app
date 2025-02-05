package com.epm.gestepm.model.personalexpensesheet.dao.entity;

import com.epm.gestepm.lib.audit.AuditCreateApprovePaidDischarge;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonalExpenseSheet implements AuditCreateApprovePaidDischarge, Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer projectId;

  @NotNull
  private String description;

  private PersonalExpenseSheetStatusEnum status;

  private String observations;

  private Double amount;

  private List<Integer> personalExpenseIds;

  @NotNull
  private LocalDateTime createdAt;

  @NotNull
  private Integer createdBy;

  private LocalDateTime approvedAt;

  private Integer approvedBy;

  private LocalDateTime paidAt;

  private Integer paidBy;

  private LocalDateTime dischargedAt;

  private Integer dischargedBy;

}
