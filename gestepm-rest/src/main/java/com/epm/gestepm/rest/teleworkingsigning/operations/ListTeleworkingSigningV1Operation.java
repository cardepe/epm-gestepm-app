package com.epm.gestepm.rest.teleworkingsigning.operations;

import com.epm.gestepm.lib.controller.APIOperation;
import com.epm.gestepm.rest.teleworkingsigning.request.TeleworkingSigningListRestRequest;
import com.epm.gestepm.restapi.openapi.api.TeleworkingSigningV1Api;

public class ListTeleworkingSigningV1Operation extends APIOperation<TeleworkingSigningV1Api, TeleworkingSigningListRestRequest> {

    public ListTeleworkingSigningV1Operation() {
        super("listTeleworkingSigningV1");

        this.generateLinksWith((apiClass, req) -> apiClass.listTeleworkingSigningsV1(req.getMeta(),
                req.getLinks(), req.getExpand(), req.getOffset(), req.getLimit(), req.getOrder(), req.getOrderBy(), req.getIds(), req.getUserIds(), req.getProjectIds(), req.getCurrent()));
    }

}
