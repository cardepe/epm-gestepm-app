package com.epm.gestepm.rest.teleworkingsigning.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningFindRestRequest;
import com.epm.gestepm.restapi.openapi.api.TeleworkingSigningV1Api;

public class FindTeleworkingSigningV1Operation extends APIOperation<TeleworkingSigningV1Api, TeleworkingSigningFindRestRequest> {

    public FindTeleworkingSigningV1Operation() {
        super("findTeleworkingSigningV1");

        this.generateLinksWith(
                (apiClass, req) -> apiClass.findTeleworkingSigningByIdV1(req.getId(), req.getMeta(), req.getLinks(), req.getExpand()));
    }

}
