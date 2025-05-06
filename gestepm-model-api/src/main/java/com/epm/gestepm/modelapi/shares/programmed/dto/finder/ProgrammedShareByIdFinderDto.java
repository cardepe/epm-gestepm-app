package com.epm.gestepm.modelapi.shares.programmed.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammedShareByIdFinderDto {

    @NotNull
    private Integer id;

}
