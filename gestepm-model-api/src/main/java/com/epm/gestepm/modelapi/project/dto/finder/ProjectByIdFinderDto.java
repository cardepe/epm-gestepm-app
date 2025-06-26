package com.epm.gestepm.modelapi.project.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectByIdFinderDto {

    @NotNull
    private Integer id;

}
