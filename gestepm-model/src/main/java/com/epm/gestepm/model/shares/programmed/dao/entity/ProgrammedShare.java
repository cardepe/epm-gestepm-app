package com.epm.gestepm.model.shares.programmed.dao.entity;

import com.epm.gestepm.lib.audit.AuditCreateClose;
import com.epm.gestepm.lib.audit.AuditCreateUpdate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProgrammedShare implements AuditCreateClose, Serializable {

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

    private Integer secondTechnicalId;

    private String secondTechnicalName;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String observations;

    private String customerSignature;

    private String operatorSignature;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private Integer createdBy;

    private LocalDateTime closedAt;

    private Integer closedBy;

    private Set<Integer> fileIds;

}
