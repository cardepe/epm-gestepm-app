package com.epm.gestepm.modelapi.personalexpensesheet.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PersonalExpenseSheetDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

    private PersonalExpenseSheetStatusEnumDto status;

    private String observations;

    private Double amount;

    private Set<Integer> personalExpenseIds;

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
