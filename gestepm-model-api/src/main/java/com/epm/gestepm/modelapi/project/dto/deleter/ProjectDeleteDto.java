package com.epm.gestepm.modelapi.project.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectDeleteDto {

    @NotNull
    private Integer id;

}
