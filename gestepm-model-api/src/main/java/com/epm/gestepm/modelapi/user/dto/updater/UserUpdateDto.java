package com.epm.gestepm.modelapi.user.dto.updater;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surnames;

    @NotNull
    private String email;

    private String password;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private Integer state;

    @NotNull
    private Integer signingId;

    private String forumUsername;

    private String forumPassword;

    @NotNull
    private Integer roleId;

    private Integer levelId;

    @NotNull
    private Double workingHours;

    private Integer currentYearHolidaysCount;

    private Integer lastYearHolidaysCount;

}
