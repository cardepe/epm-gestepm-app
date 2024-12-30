package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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
  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String description;

  private Integer familyId;

  private Integer subFamilyId;

  private Integer topicId;

  private String forumTitle;

  @NotNull
  private NoProgrammedShareStateEnum state;

  @Singular
  private List<Integer> inspectionIds;

  @Singular
  private Set<Integer> fileIds;

}
