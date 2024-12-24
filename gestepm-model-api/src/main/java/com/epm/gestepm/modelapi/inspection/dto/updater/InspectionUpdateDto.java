package com.epm.gestepm.modelapi.inspection.dto.updater;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionFileCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.MaterialCreateDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class InspectionUpdateDto {

    @NotNull
    private Integer id;

    private Integer userSigningId;

    private Integer shareId;

    private ActionEnumDto action;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private String description;

    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private List<MaterialCreateDto> materials;

    private InspectionFileCreateDto materialsFile;

    private Integer equipmentHours;

    private Integer topicId;

    private Set<InspectionFileCreateDto> files;

    private Boolean notify;

}
