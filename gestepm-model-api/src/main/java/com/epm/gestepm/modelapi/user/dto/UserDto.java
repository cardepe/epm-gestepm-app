package com.epm.gestepm.modelapi.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surnames;

    @NotNull
    private String email;

    @NotNull
    private Integer activityCenterId;

    @NotNull
    private Integer state;

    @NotNull
    private Integer signingId;

    private String forumUsername;

    @NotNull
    private Integer roleId;

    private Integer levelId;

    @NotNull
    private Double workingHours;

    @NotNull
    private Integer currentYearHolidaysCount;

    @NotNull
    private Integer lastYearHolidaysCount;

}
