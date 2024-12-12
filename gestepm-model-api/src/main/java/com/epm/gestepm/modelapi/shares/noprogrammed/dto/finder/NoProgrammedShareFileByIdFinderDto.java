package com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NoProgrammedShareFileByIdFinderDto {

    @NotNull
    private Integer id;

}
