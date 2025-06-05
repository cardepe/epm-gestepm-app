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

    private String name;

    private String surnames;

    private String email;

    private String password;

    private Integer activityCenterId;

    private Integer state;

    private Integer signingId;

    private String forumUsername;

    private String forumPassword;

    private Integer roleId;

    private Integer levelId;

    private Double workingHours;

    private Integer currentYearHolidaysCount;

    private Integer lastYearHolidaysCount;

}
