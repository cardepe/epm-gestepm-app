package com.epm.gestepm.modelapi.project.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectMemberCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

}
