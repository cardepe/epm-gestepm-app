package com.epm.gestepm.modelapi.shares.programmed.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ProgrammedShareFileByIdFinderDto {

    @NotNull
    private Integer id;

}
