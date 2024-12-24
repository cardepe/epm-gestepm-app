package com.epm.gestepm.modelapi.inspection.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionByIdFinderDto {

    @NotNull
    private Integer id;

}
