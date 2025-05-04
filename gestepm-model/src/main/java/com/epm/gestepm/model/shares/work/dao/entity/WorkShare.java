package com.epm.gestepm.model.shares.work.dao.entity;

import com.epm.gestepm.lib.audit.AuditCreateClose;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class WorkShare implements AuditCreateClose, Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private String username;

    @NotNull
    private Integer projectId;

    @NotNull
    private String projectName;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

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
