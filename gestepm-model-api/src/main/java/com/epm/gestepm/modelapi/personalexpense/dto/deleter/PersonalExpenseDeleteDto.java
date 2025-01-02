package com.epm.gestepm.modelapi.personalexpense.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalExpenseDeleteDto {

    @NotNull
    private Integer id;

}
