package com.epm.gestepm.modelapi.inspection.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionFileDeleteDto {

    @NotNull
    private Integer id;

}
