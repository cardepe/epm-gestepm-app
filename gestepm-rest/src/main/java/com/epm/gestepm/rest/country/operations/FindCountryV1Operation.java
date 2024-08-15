package com.epm.gestepm.rest.country.operations;

import com.epm.gestepm.rest.country.request.CountryFindRestRequest;
import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.restapi.openapi.api.CountriesV1Api;

public class FindCountryV1Operation extends APIOperation<CountriesV1Api, CountryFindRestRequest> {

    public FindCountryV1Operation() {
        super("findCountryV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findCountryByIdV1(req.getId(), req.getMeta(), req.getLinks()));
    }

}
