package com.epm.gestepm.model.inspection.dao.entity;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class Inspection implements Serializable {

  @NotNull
  private Integer id;

  private Integer userSigningId;

  @NotNull
  private Integer shareId;

  @NotNull
  private ActionEnum action;

  @NotNull
  private OffsetDateTime startDate;

  private OffsetDateTime endDate;

  private String description;

  @NotNull
  private Integer firstTechnicalId;

  private Integer secondTechnicalId;

  private String signature;

  private String operatorSignature;

  private String clientName;

  private Set<Material> materials = new HashSet<>();

  private String materialsFile;

  private String materialsFileExtension;

  private Integer equipmentHours;

  private Integer topicId;

  @Singular
  private Set<Integer> fileIds;

}
