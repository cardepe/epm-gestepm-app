package com.epm.gestepm.masterdata.api.holiday.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HolidayDeleteDto {

    @NotNull
    private Integer id;

}
