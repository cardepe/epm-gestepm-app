package com.epm.gestepm.modelapi.projectmaterial.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ProjectMaterialUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private Integer projectId;

    @NotNull
    private String nameEs;

    @NotNull
    private String nameFr;

    @NotNull
    private Boolean required;

}
