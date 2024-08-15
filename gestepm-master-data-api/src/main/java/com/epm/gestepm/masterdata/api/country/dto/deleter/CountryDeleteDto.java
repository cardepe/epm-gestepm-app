package com.epm.gestepm.masterdata.api.country.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CountryDeleteDto {

    @NotNull
    private Integer id;

}
