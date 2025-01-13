package com.epm.gestepm.modelapi.inspection.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class InspectionDto implements Serializable {

    @NotNull
    private Integer id;

    private Integer userSigningId;

    @NotNull
    private Integer shareId;

    @NotNull
    private ActionEnumDto action;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private Set<MaterialDto> materials = new HashSet<>();

    private String materialsFile;

    private String materialsFileName;

    private Integer equipmentHours;

    private Integer topicId;

    private List<Integer> fileIds;

    public Integer getOrder(final List<Integer> inspectionIds) {
        return inspectionIds.indexOf(this.id) + 1;
    }

    public Integer getInspectionTypeNumber(final List<Integer> inspectionIds) {
        final int index = inspectionIds.indexOf(this.id);
        return ((index - 1) / 3) + 1;
    }
}
