package com.epm.gestepm.masterdata.api.country.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CountryUpdateDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String tag;

}
