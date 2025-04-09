package com.epm.gestepm.modelapi.shares.construction.dto;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ConstructionShareDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private String observations;

    private String operatorSignature;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer createdBy;

    private LocalDateTime closedAt;

    private Integer closedBy;

    private Set<Integer> fileIds;

}
