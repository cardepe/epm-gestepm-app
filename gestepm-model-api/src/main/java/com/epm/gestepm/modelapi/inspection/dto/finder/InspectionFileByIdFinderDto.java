package com.epm.gestepm.modelapi.inspection.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class InspectionFileByIdFinderDto {

    @NotNull
    private Integer id;

}
