package com.epm.gestepm.modelapi.personalexpensesheet.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonalExpenseSheetCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private String description;

}
