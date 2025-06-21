package com.epm.gestepm.rest.customer.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.customer.request.CustomerFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.CountriesV1Api;
import com.epm.gestepm.restapi.openapi.api.ProjectCustomerV1Api;

public class FindCustomerV1Operation extends APIOperation<ProjectCustomerV1Api, CustomerFindRestRequest> {

    public FindCustomerV1Operation() {
        super("findCustomerV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findCustomerByProjectIdV1(req.getProjectId()));
    }

}
