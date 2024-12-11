package com.epm.gestepm.modelapi.inspection.dto.updater;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class InspectionUpdateDto {

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
    private String description;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private List<Integer> materialIds;

    private String materialsFile;

    private String materialsFileExtension;

    private Integer equipmentHours;

    private Integer topicId;

}
