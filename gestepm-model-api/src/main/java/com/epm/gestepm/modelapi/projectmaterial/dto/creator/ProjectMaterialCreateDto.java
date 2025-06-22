package com.epm.gestepm.modelapi.projectmaterial.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectMaterialCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

}
