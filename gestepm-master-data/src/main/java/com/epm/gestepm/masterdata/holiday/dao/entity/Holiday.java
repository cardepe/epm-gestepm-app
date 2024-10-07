package com.epm.gestepm.masterdata.holiday.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class Holiday implements Serializable {

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
