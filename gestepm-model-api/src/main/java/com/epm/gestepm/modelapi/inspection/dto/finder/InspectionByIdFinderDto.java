package com.epm.gestepm.modelapi.inspection.dto.finder;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InspectionByIdFinderDto {

    @NotNull
    private Integer id;

}
