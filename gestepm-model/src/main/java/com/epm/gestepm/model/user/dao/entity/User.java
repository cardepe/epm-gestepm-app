package com.epm.gestepm.model.user.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class User implements Serializable {

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
