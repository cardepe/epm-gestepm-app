package com.epm.gestepm.modelapi.personalexpense.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PersonalExpenseFileByIdFinderDto {

    @NotNull
    private Integer id;

}
