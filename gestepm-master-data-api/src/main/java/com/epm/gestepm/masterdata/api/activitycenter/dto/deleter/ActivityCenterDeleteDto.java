package com.epm.gestepm.masterdata.api.activitycenter.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActivityCenterDeleteDto {

    @NotNull
    private Integer id;

}
