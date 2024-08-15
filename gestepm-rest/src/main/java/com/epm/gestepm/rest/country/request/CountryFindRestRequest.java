package com.epm.gestepm.rest.country.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryFindRestRequest extends RestRequest {

    @NotNull
    private Integer id;

}
