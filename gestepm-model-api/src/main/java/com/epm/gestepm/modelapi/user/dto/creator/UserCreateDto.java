package com.epm.gestepm.modelapi.user.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserCreateDto {

    @NotNull
    private String name;

    @NotNull
    private String surnames;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private Integer signingId;

    @NotNull
    private Integer roleId;

    private Integer levelId;

}
