package com.epm.gestepm.modelapi.personalexpensesheet.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalExpenseSheetUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

}
