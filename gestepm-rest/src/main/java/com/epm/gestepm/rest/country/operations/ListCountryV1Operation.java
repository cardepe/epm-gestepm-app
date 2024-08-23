package com.epm.gestepm.rest.country.operations;

import com.epm.gestepm.rest.country.request.CountryListRestRequest;
import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.restapi.openapi.api.CountriesV1Api;

public class ListCountryV1Operation extends APIOperation<CountriesV1Api, CountryListRestRequest> {

    public ListCountryV1Operation() {
        super("listCountryV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.listCountriesV1(req.getMeta(), req.getLinks(), req.getOffset(),
                        req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(), req.getName(), req.getTags()));
    }

}
