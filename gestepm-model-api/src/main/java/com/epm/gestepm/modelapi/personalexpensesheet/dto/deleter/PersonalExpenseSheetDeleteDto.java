package com.epm.gestepm.modelapi.personalexpensesheet.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalExpenseSheetDeleteDto {

    @NotNull
    private Integer id;

}
