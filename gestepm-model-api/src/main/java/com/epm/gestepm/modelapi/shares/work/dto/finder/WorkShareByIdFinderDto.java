package com.epm.gestepm.modelapi.shares.work.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkShareByIdFinderDto {

    @NotNull
    private Integer id;

}
