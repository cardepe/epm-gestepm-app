package com.epm.gestepm.masterdata.api.holiday.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class HolidayDto implements Serializable {

    @NotNull
    private Integer id;

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
