package com.epm.gestepm.rest.country.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CountryListRestRequest extends RestRequest {

    private List<Integer> ids;

    private String name;

    private List<String> tags;

}
