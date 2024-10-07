package com.epm.gestepm.masterdata.api.holiday.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HolidayCreateDto {

    @NotNull
    private String name;

    @NotNull
    private Integer day;

    @NotNull
    private Integer month;

    @NotNull
    private Integer countryId;

    private Integer activityCenterId;

}
