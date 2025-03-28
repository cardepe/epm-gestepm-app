package com.epm.gestepm.model.teleworkingsigning.dao.entity;

import com.epm.gestepm.lib.audit.AuditCreateApprovePaidDischarge;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeleworkingSigning implements AuditCreateApprovePaidDischarge, Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private Integer projectId;

  @NotNull
  private LocalDateTime startedAt;

  private String startedLocation;

  private LocalDateTime closedAt;

  private String closedLocation;

}
