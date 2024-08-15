package com.epm.gestepm.masterdata.api.activitycenter.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActivityCenterCreateDto {

    @NotNull
    private String name;

    @NotNull
    private Integer countryId;

}
