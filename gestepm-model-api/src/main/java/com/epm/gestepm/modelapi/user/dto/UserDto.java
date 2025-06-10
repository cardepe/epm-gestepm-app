package com.epm.gestepm.modelapi.user.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    @NotNull
    private Integer currentYearHolidaysCount;

    @NotNull
    private Integer lastYearHolidaysCount;

    public String getFullName() {
        final StringBuilder builder = new StringBuilder();

        builder.append(this.name);

        if (StringUtils.isNoneBlank(this.surnames)) {
            builder.append(" ").append(this.surnames);
        }

        return builder.toString();
    }
}
