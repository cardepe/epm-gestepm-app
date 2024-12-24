package com.epm.gestepm.modelapi.inspection.dto.creator;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionCreateDto {

    private Integer userSigningId;

    @NotNull
    private Integer shareId;

    @NotNull
    private ActionEnumDto action;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

}
