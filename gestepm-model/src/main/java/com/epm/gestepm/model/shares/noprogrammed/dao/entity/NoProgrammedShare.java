package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class NoProgrammedShare implements Serializable {

  @NotNull
  private Integer id;

  @NotNull
  private Integer userId;

  @NotNull
  private Integer projectId;

  private Integer userSigningId;

  @NotNull
  private OffsetDateTime startDate;

  private OffsetDateTime endDate;

  @NotNull
  private String description;

  @NotNull
  private Integer familyId;

  @NotNull
  private Integer subFamilyId;

  private Integer topicId;

  private String forumTitle;

  @NotNull
  private Integer state;

  @NotNull
  private Integer lastDiagnosis;

  @Singular
  private Set<Integer> interventionIds;

  @Singular
  private Set<Integer> fileIds;

}
