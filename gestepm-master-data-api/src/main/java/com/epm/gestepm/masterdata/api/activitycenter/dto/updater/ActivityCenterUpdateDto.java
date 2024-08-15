package com.epm.gestepm.masterdata.api.activitycenter.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActivityCenterUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer countryId;

}
