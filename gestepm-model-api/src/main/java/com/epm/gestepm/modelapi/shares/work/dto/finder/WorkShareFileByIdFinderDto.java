package com.epm.gestepm.modelapi.shares.work.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class WorkShareFileByIdFinderDto {

    @NotNull
    private Integer id;

}
