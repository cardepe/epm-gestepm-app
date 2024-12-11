package com.epm.gestepm.modelapi.inspection.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
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
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private Set<Integer> materialIds;

    private String materialsFile;

    private String materialsFileExtension;

    private Integer equipmentHours;

    private Integer topicId;

    private Set<Integer> fileIds;

}
