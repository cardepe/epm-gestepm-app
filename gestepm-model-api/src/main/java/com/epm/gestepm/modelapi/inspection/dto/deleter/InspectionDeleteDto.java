package com.epm.gestepm.modelapi.inspection.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class InspectionDeleteDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

}
