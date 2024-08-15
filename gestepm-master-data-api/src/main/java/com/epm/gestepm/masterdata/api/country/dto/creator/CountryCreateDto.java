package com.epm.gestepm.masterdata.api.country.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CountryCreateDto {

    @NotNull
    private String name;

    @NotNull
    private String tag;

}
