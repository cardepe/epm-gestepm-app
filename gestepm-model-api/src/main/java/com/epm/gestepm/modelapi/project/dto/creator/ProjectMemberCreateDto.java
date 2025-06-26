package com.epm.gestepm.modelapi.project.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ProjectMemberCreateDto {

    @NotNull
    private Integer projectId;

    @NotNull
    private Integer userId;

}
